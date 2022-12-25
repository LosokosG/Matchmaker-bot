package matchmaking;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayPing extends ListenerAdapter {


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().equalsIgnoreCase("playMessage") && event.getMember().getId().equals("587059393766359041") ){
            event.getMessage().delete().queue();
           TextChannel textChannel = event.getChannel().asTextChannel();

            MessageEmbed embed = new EmbedBuilder()
                    .setColor(Color.getHSBColor(2,171,235))
                    .setTitle("Ch\u0119tny do gry?")
                    .addField("Zareaguj", "Je\u017Celi szukasz aktualnie kogo\u015B do gry, zareaguj na t\u0105 wiadomo\u015B\u0107 aby otrzyma\u0107 rol\u0119 " + event.getGuild().getRoleById("1056351374687879168").getAsMention(), false)
                    .addField("Komenda", "Mo\u017Cesz r\u00F3wnie\u017C u\u017Cy\u0107 komendy **/gram** lub **/g** aby otrzyma\u0107 rol\u0119 " + event.getGuild().getRoleById("1056351374687879168").getAsMention(), true)
                    .build();

           textChannel.sendMessageEmbeds(embed).queue();

            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            executor.schedule(() -> {
                String messageId = textChannel.getLatestMessageId();
                System.out.println(messageId);
                Message message = textChannel.retrieveMessageById(messageId).complete();
                message.addReaction(Emoji.fromCustom("pixel_hand_thumbs_up_right", Long.parseLong("1056360096604631170"), false)).queue();
            }, 1, TimeUnit.SECONDS); // Delay of 5 seconds


        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getInteraction().getName().equals("g") || event.getInteraction().getName().equals("gram")) {
            if (!event.getMember().getRoles().contains(event.getGuild().getRoleById("1056351374687879168"))) {
                event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("1056351374687879168")).queue();
                event.reply("Przyznano rol\u0119 " + event.getGuild().getRoleById("1056351374687879168").getAsMention()).setEphemeral(true).queue();
            } else {
                event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("1056351374687879168")).queue();
                event.reply("Odebrano rol\u0119 " + event.getGuild().getRoleById("1056351374687879168").getAsMention()).setEphemeral(true).queue();
            }
        }
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        if(event.getChannel().getId().equals("1055078416036134922") && event.getReaction().getEmoji().equals(Emoji.fromCustom("pixel_hand_thumbs_up_right", Long.parseLong("1056360096604631170"), false)))
        event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("1056351374687879168")).queue();
    }

    @Override
    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {
        if(event.getChannel().getId().equals("1055078416036134922") && event.getReaction().getEmoji().equals(Emoji.fromCustom("pixel_hand_thumbs_up_right", Long.parseLong("1056360096604631170"), false)))
            event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("1056351374687879168")).queue();
    }
}
