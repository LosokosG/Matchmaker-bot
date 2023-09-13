
import Dev.sendRoleMessage;
import events.secretPhrase;
import matchmaking.PlayPing;
import matchmaking.RoleNames;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;


public class BotInit {

    public static void main(String[] args) throws Exception {

      boolean isTesting = Boolean.parseBoolean(System.getenv("isTesting"));

        String token = System.getenv("TOKEN");
        JDABuilder jdaBuilder = JDABuilder.createDefault(token)
                .setActivity(Activity.playing("Dark and Darker"))
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setAutoReconnect(true);
        JDA jda;

        if(isTesting)
            jda = jdaBuilder.addEventListeners(new secretPhrase(), new sendRoleMessage())
                    .build().awaitReady();    //Środowisko testowe
        else
            jda = jdaBuilder.addEventListeners(new RoleNames(), new PlayPing(), new sendRoleMessage())
                    .build().awaitReady(); //Serwer główny

        Guild guild;

        if(isTesting)
            guild = jda.getGuildById("1046510699809079448"); //Testing guild
        else
            guild = jda.getGuildById("1054726822337720340"); //Dark and Darker Polska Guild

        //Commands
        if( guild != null && !isTesting) {

            jda.upsertCommand("g", "Dostajesz rol\u0119 @Ping do gry").queue();
            jda.upsertCommand("gram", "Dostajesz rol\u0119 @Ping do gry (Skr\u00F3t komendy: /g ").queue();
            jda.upsertCommand("sendrolemessage", "Wysyła zaktualizowaną wersję wiadomości pozwalającej na zmianę klasy na serwerze").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR)).queue();
        }
        else if (guild != null && isTesting){
            //Tutaj wszystkie komendy aktualnie w developmencie


        }
    }
}

