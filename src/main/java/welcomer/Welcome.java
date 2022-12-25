package welcomer;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Welcome extends ListenerAdapter {

    //Po żegnanie
    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        event.getGuild().getTextChannelById("1055940864750399508").sendMessage(event.getMember().getAsMention() + " Wyszedl z serwera, lekki przyps ;(").queue();
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        MessageEmbed embed = new EmbedBuilder()
                .addField("\uD83D\uDD0E | matchmaking","Szukasz grupy do gry w D&amp;D? Świetnie trafiłeś! Dołącz do naszego matchmakingu na kanale **\uD83D\uDD0E | matchmaking**\n" +
                        "i szybko znajdź swoich towarzyszy do gry.", false)
                .addField("\u2795 | Dodaj kanał", "A jeśli wolisz stworzyć własny prywatny kanał, zrób to za pomocą **\u2795 | Dodaj kanał**" , false)
                .addField("\uD83C\uDFF9role", "Pamiętaj tylko, aby oznaczyć swoją rolę na **\uD83C\uDFF9role**, żeby inni gracze wiedzieli, jakiej postaci się podejmujesz.", false)
                .build();


        event.getGuild().getTextChannelById("1055180960825606194").sendMessage( "Siema "+ event.getMember().getAsMention() + "!" + embed).queue();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().equals("abrakadabra")) {
            MessageEmbed embed = new EmbedBuilder()
                    .setColor(Color.red)
                    .addField("\uD83D\uDD0E | matchmaking","Szukasz grupy do gry w D&D? \u015Awietnie trafi\u0142e\u015B! Do\u0142\u0105cz do naszego matchmakingu na kanale \uD83D\uDD0E | matchmaking i szybko znajd\u017A swoich towarzyszy do gry. Potrzebujesz grupy, \u017Ceby cieszy\u0107 si\u0119 gr\u0105 w D&D? Nasz matchmaking jest tu, \u017Ceby Ci pom\u00F3c! Do\u0142\u0105cz do nas i znajd\u017A swoich towarzyszy do gry szybko i \u0142atwo.", false)
                    .addBlankField(false)
                    .addField("\u2795 | Dodaj kana\u0142", "A je\u015Bli wolisz stworzy\u0107 w\u0142asny prywatny kana\u0142, zr\u00F3b to za pomoc\u0105 \u2795 | Dodaj kana\u0142. Cenisz sobie prywatno\u015B\u0107 i chcesz stworzy\u0107 w\u0142asny kana\u0142 tylko dla siebie i swoich znajomych? Skorzystaj z naszej opcji dodawania kana\u0142u i stw\u00F3rz w\u0142asny prywatny kana\u0142, \u017Ceby cieszy\u0107 si\u0119 gr\u0105 w D&D w swoim w\u0142asnym towarzystwie." , false)
                    .addBlankField(false)
                    .addField("\uD83C\uDFF9role", "Pami\u0119taj tylko, aby oznaczy\u0107 swoj\u0105 rol\u0119 na \uD83C\uDFF9role, \u017Ceby inni gracze wiedzieli, jakiej postaci si\u0119 podejmujesz. Chcesz, \u017Ceby inni gracze wiedzieli, jak\u0105 postaci\u0105 graj\u0105? Oznacz swoj\u0105 rol\u0119 na \uD83C\uDFF9role, \u017Ceby inni gracze wiedzieli, z jak\u0105 postaci\u0105 masz do czynienia.", false)
                    .build();


            event.getGuild().getTextChannelById("1055180960825606194").sendMessage("Siema " + event.getMember().getAsMention() + "!").addEmbeds(embed).queue();
        }
    }
}
