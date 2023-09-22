package trading;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.requests.RestAction;
import org.bson.Document;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class funcMethods extends tradingChannels {

    public static MongoClient MongoConnection(){
        ConnectionString connectionString = new ConnectionString("mongodb+srv://losokos:" + System.getenv("MONGO")+ "@darkanddarkerpolska.ksmqiog.mongodb.net/");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(settings);
    }

    public static Modal tradingModal(ButtonInteractionEvent event , String rola){

        if(rola != "middleman") {
            TextInput graczInput = TextInput.create("graczInput", "Nazwa/ID gracza do handlu", TextInputStyle.SHORT)
                    .setPlaceholder(event.getMember().getEffectiveName() + " lub " + event.getMember().getUser().getId())
                    .setRequiredRange(0, 100)
                    .setRequired(true)
                    .build();


            TextInput przedmiotInput = null;
            if (rola == "kupiec")
                przedmiotInput = TextInput.create("przedmiotInput", "Jaki przedmiot kupujesz?", TextInputStyle.SHORT)
                        .setPlaceholder("np: 10 kluczy")
                        .setRequiredRange(0, 100)
                        .setRequired(false)
                        .build();

            else if (rola == "sprzedawca")
                przedmiotInput = TextInput.create("przedmiotInput", "Jaki przedmiot sprzedajesz?", TextInputStyle.SHORT)
                        .setPlaceholder("np: 10 kluczy")
                        .setRequiredRange(0, 100)
                        .setRequired(false)
                        .build();

            TextInput rolaInput = TextInput.create("rolaInput", "Kupiec czy sprzedawca? (nie zmieniaj)", TextInputStyle.SHORT)
                    .setValue(rola)
                    .setPlaceholder(rola)
                    .setRequiredRange(0, 100)
                    .setRequired(true)
                    .build();

            Modal modal = Modal.create("handelGraczyModal", "Nowy kana\u0142 handlu")
                    .addActionRows(ActionRow.of(graczInput), ActionRow.of(przedmiotInput), ActionRow.of(rolaInput)).build();
            return modal;
        }
        else{


            //TUTAJ DO ZMIANY!!!!
            TextInput graczInput = TextInput.create("graczInput", "Nazwa/ID gracza do handlu", TextInputStyle.SHORT)
                    .setPlaceholder(event.getMember().getEffectiveName() + " lub " + event.getMember().getUser().getId())
                    .setRequiredRange(0, 100)
                    .setRequired(true)
                    .build();


            TextInput przedmiotInput = null;
            if (rola == "kupiec")
                przedmiotInput = TextInput.create("przedmiotInput", "Jaki przedmiot kupujesz?", TextInputStyle.SHORT)
                        .setPlaceholder("np: 10 kluczy")
                        .setRequiredRange(0, 100)
                        .setRequired(false)
                        .build();

            else if (rola == "sprzedawca")
                przedmiotInput = TextInput.create("przedmiotInput", "Jaki przedmiot sprzedajesz?", TextInputStyle.SHORT)
                        .setPlaceholder("np: 10 kluczy")
                        .setRequiredRange(0, 100)
                        .setRequired(false)
                        .build();

            TextInput rolaInput = TextInput.create("rolaInput", "Kupiec czy sprzedawca? (nie zmieniaj)", TextInputStyle.SHORT)
                    .setValue(rola)
                    .setPlaceholder(rola)
                    .setRequiredRange(0, 100)
                    .setRequired(true)
                    .build();

            Modal modal = Modal.create("handelMiddlemanModal", "Nowy kana\u0142 handlu")
                    .addActionRows(ActionRow.of(graczInput), ActionRow.of(przedmiotInput), ActionRow.of(rolaInput)).build();
            return modal;
        }
    }


    public static void createTradingChannel(ModalInteractionEvent event, boolean isMiddleManPresent, String invitedMemberID, String tradedItem, String creatorTradeRole){
        MongoClient mongoClient = funcMethods.MongoConnection();
        MongoDatabase database = mongoClient.getDatabase("Trading");
        MongoCollection<Document> channelsCollection = database.getCollection("channels");

        if(!isMiddleManPresent) {

            if(tradedItem.isBlank())
                tradedItem = "Nie podano przedmiotu.";

            Guild guild = event.getGuild();
            Role everyoneRole = guild.getRoleById("1046510699809079448"); // ZMIENIAC ID MIEDZY SERWEREM TESTOWYM A ZWYKŁYM
            Role graczRole = guild.getRoleById("1154522982769774734");// ZMIENIAC ID MIEDZY SERWEREM TESTOWYM A ZWYKŁYM
            Member creator = event.getMember();
            Member invitedMember = guild.getMemberById(invitedMemberID);

            Category RMTmarketCategory = guild.getCategoryById("1154518031435837440");

            Collection<Permission> memberAllowPerms = new ArrayList<>();
            memberAllowPerms.add(Permission.VIEW_CHANNEL);

            Collection<Permission> memberDenyPerms = new ArrayList<>();
            memberDenyPerms.add(Permission.MESSAGE_MANAGE);
            memberDenyPerms.add(Permission.CREATE_PRIVATE_THREADS);
            memberDenyPerms.add(Permission.CREATE_PUBLIC_THREADS);
            memberDenyPerms.add(Permission.MESSAGE_SEND);
            memberDenyPerms.add(Permission.MESSAGE_ADD_REACTION);

            Collection<Permission> roleDenyPerms = new ArrayList<>();
            roleDenyPerms.add(Permission.VIEW_CHANNEL);

            String finalTradedItem = tradedItem;
            RMTmarketCategory.createTextChannel(creator.getUser().getName().toLowerCase() + " room")
                    .addMemberPermissionOverride(creator.getIdLong(), memberAllowPerms, memberDenyPerms)
                    .addMemberPermissionOverride(invitedMember.getIdLong(), memberAllowPerms, memberDenyPerms)
                    .addRolePermissionOverride(everyoneRole.getIdLong(), null, roleDenyPerms)
                    .addRolePermissionOverride(graczRole.getIdLong(), null, roleDenyPerms)
                    .setTopic("Prywatny kana\u0142 handlu mi\u0119dzy: " + creator.getAsMention() + " a " + invitedMember.getAsMention())
                    .queue(channel -> {
                        channel.sendMessageEmbeds(newChannelMessage(false, creator, creatorTradeRole, invitedMember, finalTradedItem)).queue(message -> {
                            message.pin().queue();
                            message.addReaction(Emoji.fromUnicode("\uD83D\uDC4D")).queue();
                            System.out.println(channel.getPermissionOverrides());
                        });

                        Document document = new Document()
                                .append("channelID", channel.getId())
                                .append("creatorID", creator.getId())
                                .append("invitedMemberID", invitedMemberID)
                                .append("tradedItem", finalTradedItem)
                                .append("isMiddleManPresent", false)
                                .append("isActive", false);

                        channelsCollection.insertOne(document);

                        event.getHook().editOriginal("Nowy kana\u0142 do RMT zosta\u0142 stworzony: " + channel.getAsMention()).queue();
                    });
        }


    }

    public static MessageEmbed newChannelMessage(boolean isMiddleManPresent, Member creator, String creatorTradeRole, Member invitedMember, String tradedItem){
        EmbedBuilder embedBuilder = new EmbedBuilder();

        MessageEmbed messageEmbed = null;

        embedBuilder.setColor(Color.ORANGE);
        embedBuilder.addField("Ustalony przedmiot wymiany: ", "**" + tradedItem + "**", false);


        if(!isMiddleManPresent)
            if(creatorTradeRole.equals("kupiec"))
                messageEmbed = embedBuilder.setTitle("Stworzono prywatny trade room!")
                    .addField("", "**Kupuj\u0105cy:**" + creator.getAsMention(), false)
                    .addField("", "**Sprzedawca:**" + invitedMember.getAsMention(), false)
                    .build();

            else if(creatorTradeRole.equals("sprzedawca"))
                messageEmbed = embedBuilder.setTitle("Stworzono prywatny trade room!")
                        .addField("", "**Kupuj\u0105cy:** " + invitedMember.getAsMention(), false)
                        .addField("", "**Sprzedawca:** " + creator.getAsMention(), false)
                        .build();

        else{
            messageEmbed = embedBuilder.setTitle("Stworzono prywatny trade room!")
                    .addField("test", "DSADSAa", false)
                    .build();
        }

        return messageEmbed;
    }
}