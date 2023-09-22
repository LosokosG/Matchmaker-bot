package trading;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Collection;

import static trading.funcMethods.createTradingChannel;
import static trading.funcMethods.tradingModal;

public class tradingChannels extends ListenerAdapter {

    String middlemanRoleId = "1154509252526870590";
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
       if(event.getInteraction().getName().equals("addtradebutton")){
           Message latestMessage = event.getChannel().asTextChannel().retrieveMessageById(event.getChannel().asTextChannel().getLatestMessageId()).complete();
           event.getChannel().asTextChannel().sendMessageEmbeds(latestMessage.getEmbeds()).addActionRow(Button.success("tradebutton", Emoji.fromUnicode("\u2795")).withLabel("Stw\u00F3rz kana\u0142")).queue();
           event.reply("Dodano przycisk do ostatniej wiadomości").setEphemeral(true).queue();
       }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if(event.getInteraction().getButton().getId().equals("tradebutton")){

            Role middlemanRole = event.getGuild().getRoleById(middlemanRoleId);

            if(!event.getMember().getRoles().contains(middlemanRole))
            event.getInteraction().reply("Wybierz swoj\u0105 rol\u0119 w transakcji")
                    .addActionRow(Button.success("kupujebutton", Emoji.fromUnicode("\uD83D\uDED2")).withLabel("Kupuj\u0119"))
                    .addActionRow(Button.danger("sprzedajebutton", Emoji.fromUnicode("\uD83D\uDCB0")).withLabel("Sprzedaj\u0119"))
                    .setEphemeral(true)
                    .queue();
            else
                event.getInteraction().reply("Wybierz swoj\u0105 rol\u0119 w transakcji")
                        .addActionRow(Button.success("kupujebutton", Emoji.fromUnicode("\uD83D\uDED2")).withLabel("Kupuj\u0119"))
                        .addActionRow(Button.danger("sprzedajebutton", Emoji.fromUnicode("\uD83D\uDCB0")).withLabel("Sprzedaj\u0119"))
                        .addActionRow(Button.danger("middlemanbutton", Emoji.fromUnicode("\uD83D\uDCB0")).withLabel("Bezpo\u015Brednik"))
                        .setEphemeral(true)
                        .queue();


        }

        if(event.getInteraction().getButton().getId().equals("kupujebutton")){
            Modal modal = tradingModal(event, "kupiec");
            event.getInteraction().replyModal(modal).queue();
        }

        if(event.getInteraction().getButton().getId().equals("sprzedajebutton")){
            Modal modal = tradingModal(event, "sprzedawca");
            event.getInteraction().replyModal(modal).queue();
        }

        if(event.getInteraction().getButton().getId().equals("middlemanbutton")){
            Modal modal = tradingModal(event, "middleman");
            event.getInteraction().replyModal(modal).queue();
        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if(event.getInteraction().getModalId().equals("handelGraczyModal")){
            event.getInteraction().deferReply(true).queue();

            String invitedMemberID = event.getInteraction().getValue("graczInput").getAsString();
            String tradedItem = event.getInteraction().getValue("przedmiotInput").getAsString();
            String creatorTradeRole = event.getInteraction().getValue("rolaInput").getAsString();

            createTradingChannel(event, false, invitedMemberID, tradedItem, creatorTradeRole);

        }
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (event.getChannel().asTextChannel().getParentCategory().getId().equals("1154518031435837440")) {
            MongoClient mongoClient = funcMethods.MongoConnection();
            MongoDatabase database = mongoClient.getDatabase("Trading");
            MongoCollection<Document> channelsCollection = database.getCollection("channels");

            TextChannel textChannel = event.getChannel().asTextChannel();

            // Create a filter to match the channelId and isActive
            Bson filter = Filters.and(Filters.eq("channelID", textChannel.getId()), Filters.eq("isActive", false));

            // Find the document
            Document doc = channelsCollection.find(filter).first();

            // Check if a matching document was found
            if (doc != null) {
                System.out.println("Found a document: " + doc.toJson());
                Message message = textChannel.retrieveMessageById(event.getMessageIdLong()).complete();
                System.out.println(message.getReactions().get(0).getCount());
                if(message.getReactions().get(0).getCount() == 3){

                    Long creatorId = Long.valueOf(doc.getString("creatorID"));
                    Long invitedMemberId = Long.valueOf(doc.getString("invitedMemberID"));

                    Bson updateOperation = Updates.set("isActive", true);
                    channelsCollection.updateOne(filter, updateOperation);

                    event.getChannel().asTextChannel().sendMessage("Kana\u0142 zosta\u0142 aktywowany!").queue();

                    //TUTAJ KOD AKTYWUJĄCY KANAŁ

                    Collection<Permission> memberAllowPerms = new ArrayList<>();
                    memberAllowPerms.add(Permission.VIEW_CHANNEL);
                    memberAllowPerms.add(Permission.MESSAGE_SEND);
                    memberAllowPerms.add(Permission.MESSAGE_ADD_REACTION);

                    textChannel.getManager().putMemberPermissionOverride(creatorId, memberAllowPerms, null).queue();
                    textChannel.getManager().putMemberPermissionOverride(invitedMemberId, memberAllowPerms, null).queue();
                }

            } else {
                System.out.println("No document matches the provided query.");
            }
        }
    }
}
