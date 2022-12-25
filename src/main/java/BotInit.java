
import events.secretPhrase;
import matchmaking.PlayPing;
import matchmaking.RoleNames;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import welcomer.Welcome;


public class BotInit {

    public static void main(String[] args) throws Exception {

        String token = "MTA1NTkyMTUwOTA3NzYzNTExMg.GNCWwI._hP_WLY7wxDObtoh-l013VlAD6x5xh0gKxVLgA";

        JDA jda = JDABuilder.createDefault(token)
                .setActivity(Activity.playing("Dark and Darker"))
                .addEventListeners(new RoleNames(), new Welcome(), new PlayPing(), new secretPhrase())
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setAutoReconnect(true)
                .build().awaitReady();
        Guild guild = jda.getGuildById("1054726822337720340");


        //Commands
        if( guild != null) {

            jda.upsertCommand("g", "Dostajesz rol\u0119 @Ping do gry").queue();
            jda.upsertCommand("gram", "Dostajesz rol\u0119 @Ping do gry (Skr\u00F3t komendy: /g ").queue();
        }
    }
}

