package events;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class secretPhrase extends ListenerAdapter {

    boolean isActive = true;
    String secret = "abrakadabra";
//a
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().toLowerCase(Locale.ROOT).contains(secret) && isActive){
            isActive = false;
            Role reward = event.getGuild().getRoleById("1055869013391069195");
            event.getGuild().addRoleToMember(event.getMember(), reward).queue();
            event.getChannel().asTextChannel().sendMessage("Gratulacje! \n" + event.getMember().getAsMention() +" Odkry\u0142 sekretn\u0105 fraz\u0119! **"
                    + "(" + secret.toUpperCase() +")" + "** Otrzyma\u0142 w zamian rol\u0119: " + reward.getAsMention() + "\nPowodzenia nast\u0119pnym razem!").queue();
        }
    }
}
