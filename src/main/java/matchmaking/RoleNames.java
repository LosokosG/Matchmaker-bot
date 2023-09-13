package matchmaking;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RoleNames extends ListenerAdapter {


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().equals("addSelection") && event.getMember().getId().equals("587059393766359041")) {
            TextChannel textChannel = event.getChannel().asTextChannel();
            Message message = textChannel.getHistory().getMessageById("1055945880802431106");
            Guild guild = event.getGuild();
            String messageContent = "**Wybierz swoj\u0105 klas\u0119!**\n" +
                    "Witajcie Poszukiwacze Przyg\u00F3d! " +
                    "Czy faworyzujesz siebie jako okre\u015Blon\u0105 klas\u0119? " +
                    "Mo\u017Cesz pochwali\u0107 si\u0119 swoim oddaniem tej klasie z odrobin\u0105 polotu! Zareaguj na emotk\u0119 powi\u0105zan\u0105 z wybran\u0105 klas\u0119, a otrzymasz t\u0119 rol\u0119! \n" +
                    guild.getRoleById("1055200791759163402").getAsMention() + ": :axe: \n" +
                    guild.getRoleById("1055200800252624907").getAsMention() + ": :shield: \n" +
                    guild.getRoleById("1055200804279177236").getAsMention() + ": :crossed_swords: \n" +
                    guild.getRoleById("1055200807227773018").getAsMention() + ": :dagger: \n" +
                    guild.getRoleById("1055200997493977111").getAsMention() + ": :bow_and_arrow: \n" +
                    guild.getRoleById("1055201136119910521").getAsMention() + ": :crystal_ball: \n";



            SelectMenu regionSelect = SelectMenu.create("region-select")
                    .setPlaceholder("Select your class")
                    .setRequiredRange(1, 1)
                    .addOption("Barbarian", "barbarian", "", Emoji.fromUnicode("U+1F5E1"))
                    .addOption("Ranger", "ranger", "", Emoji.fromUnicode("U+1F3F9"))
                    .addOption("Wizard", "wizard", "", Emoji.fromUnicode("U+1F52E"))
                    .addOption("Rogue", "rogue", "" , Emoji.fromUnicode("U+1F5E1"))
                    .addOption("Cleric", "cleric", "", Emoji.fromUnicode("U+1F6E1"))
                    .addOption("Fighter", "fighter", "", Emoji.fromUnicode("U+2694"))
                    .build();
            textChannel.sendMessage(messageContent).addActionRow(regionSelect).queue();
        }

        if(event.getMessage().getContentRaw().equalsIgnoreCase("admin") && event.getMember().getId().equals("587059393766359041"))
            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("1055178784581623859")).queue();
        if(event.getMessage().getContentRaw().equalsIgnoreCase("noAdmin") && event.getMember().getId().equals("587059393766359041"))
            event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("1055178784581623859")).queue();

    }

    @Override
    public void onSelectMenuInteraction(@NotNull SelectMenuInteractionEvent event) {
        if(event.getSelectMenu().getId().equals("region-select")){
            Guild guild = event.getGuild();

            Role barbarian = guild.getRoleById("1055200791759163402");
            Role ranger = guild.getRoleById("1055200800252624907");
            Role wizard = guild.getRoleById("1055200804279177236");
            Role rogue = guild.getRoleById("1055200807227773018");
            Role cleric = guild.getRoleById("1055200997493977111");
            Role fighter = guild.getRoleById("1055201136119910521");
            Role bard = guild.getRoleById("1141379067359268906");
            Role warlock = guild.getRoleById("1141379724183085076");

            List<Role> roleList = new ArrayList<>();

            roleList.add(barbarian);
            roleList.add(ranger);
            roleList.add(wizard);
            roleList.add(rogue);
            roleList.add(cleric);
            roleList.add(fighter);
            roleList.add(bard);
            roleList.add(warlock);

            for (Role role : roleList){
                guild.removeRoleFromMember(event.getMember(),role).queue();
            }

            prependName(event.getMember());

            switch (event.getValues().get(0)) {
                case "barbarian":
                    event.getGuild().addRoleToMember(event.getMember(), barbarian).queue();
                    appendName(event.getMember(), barbarian, guild);
                    event.reply("Przyznano rol\u0119: " + barbarian.getAsMention()).setEphemeral(true).queue();
                    System.out.println("Przyznano rol\u0119: " + barbarian.getName() + " graczowi " + event.getMember().getUser().getName());
                    break;
                case "ranger":
                    event.getGuild().addRoleToMember(event.getMember(), ranger).queue();
                    appendName(event.getMember(), ranger, guild);
                    event.reply("Przyznano rol\u0119: " + ranger.getAsMention()).setEphemeral(true).queue();
                    System.out.println("Przyznano rol\u0119: " + ranger.getName() + " graczowi " + event.getMember().getUser().getName());
                    break;
                case "wizard":
                    event.getGuild().addRoleToMember(event.getMember(), wizard).queue();
                    appendName(event.getMember(), wizard, guild);
                    event.reply("Przyznano rol\u0119: " + wizard.getAsMention()).setEphemeral(true).queue();
                    System.out.println("Przyznano rol\u0119: " + wizard.getName() + " graczowi " + event.getMember().getUser().getName());
                    break;
                case "rogue":
                    event.getGuild().addRoleToMember(event.getMember(), rogue).queue();
                    appendName(event.getMember(), rogue, guild);
                    event.reply("Przyznano rol\u0119: " + rogue.getAsMention()).setEphemeral(true).queue();
                    System.out.println("Przyznano rol\u0119: " + rogue.getName() + " graczowi " + event.getMember().getUser().getName());
                    break;
                case "cleric":
                    event.getGuild().addRoleToMember(event.getMember(), cleric).queue();
                    appendName(event.getMember(), cleric, guild);
                    event.reply("Przyznano rol\u0119: " + cleric.getAsMention()).setEphemeral(true).queue();
                    System.out.println("Przyznano rol\u0119: " + cleric.getName() + " graczowi " + event.getMember().getUser().getName());
                    break;
                case "fighter":
                    event.getGuild().addRoleToMember(event.getMember(), fighter).queue();
                    appendName(event.getMember(), fighter, guild);
                    event.reply("Przyznano rol\u0119: " + fighter.getAsMention()).setEphemeral(true).queue();
                    System.out.println("Przyznano rol\u0119: " + fighter.getName() + " graczowi " + event.getMember().getUser().getName());
                    break;
                case "bard":
                    event.getGuild().addRoleToMember(event.getMember(), bard).queue();
                    appendName(event.getMember(), bard, guild);
                    event.reply("Przyznano rol\u0119: " + bard.getAsMention()).setEphemeral(true).queue();
                    System.out.println("Przyznano rol\u0119: " + bard.getName() + " graczowi " + event.getMember().getUser().getName());
                    break;
                case "warlock":
                    event.getGuild().addRoleToMember(event.getMember(), warlock).queue();
                    appendName(event.getMember(), warlock, guild);
                    event.reply("Przyznano rol\u0119: " + warlock.getAsMention()).setEphemeral(true).queue();
                    System.out.println("Przyznano rol\u0119: " + warlock.getName() + " graczowi " + event.getMember().getUser().getName());
                    break;
            }

        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if(event.getInteraction().getButton().getId().equals("reset")){

            Guild guild = event.getGuild();

            Role barbarian = guild.getRoleById("1055200791759163402");
            Role ranger = guild.getRoleById("1055200800252624907");
            Role wizard = guild.getRoleById("1055200804279177236");
            Role rogue = guild.getRoleById("1055200807227773018");
            Role cleric = guild.getRoleById("1055200997493977111");
            Role fighter = guild.getRoleById("1055201136119910521");
            Role bard = guild.getRoleById("1141379067359268906");
            Role warlock = guild.getRoleById("1141379724183085076");

            List<Role> roleList = new ArrayList<>();

            roleList.add(barbarian);
            roleList.add(ranger);
            roleList.add(wizard);
            roleList.add(rogue);
            roleList.add(cleric);
            roleList.add(fighter);
            roleList.add(bard);
            roleList.add(warlock);

            for (Role role : roleList){
                guild.removeRoleFromMember(event.getMember(),role).queue();
            }
            prependName(event.getMember());
            event.getInteraction().reply("Odebrano ci twoją klasę! \nJesteś zwykłym cywilem...").setEphemeral(true).queue();
            System.out.println("Odebrano rolę: " + event.getMember().getUser().getName());
        }
    }

    private void prependName(Member member){

        List<String> klasyString = new ArrayList<>();

        klasyString.add(" [BARBARIAN]");
        klasyString.add(" [RANGER]");
        klasyString.add(" [WIZARD]");
        klasyString.add(" [ROGUE]");
        klasyString.add(" [CLERIC]");
        klasyString.add(" [FIGHTER]");
        klasyString.add(" [BARD]");
        klasyString.add(" [WARLOCK]");

        String memberName = member.getEffectiveName();

        for(String klasa : klasyString) {
            if (memberName.contains(klasa)){
                String newName = memberName.replace(klasa, "");
                member.modifyNickname(newName).queue();
            }
        }
    }


    private void appendName(Member member, Role roleAdded, Guild guild){

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        executor.schedule(() -> {
            //Role klas w grze:
            Role barbarian = guild.getRoleById("1055200791759163402");
            Role ranger = guild.getRoleById("1055200800252624907");
            Role wizard = guild.getRoleById("1055200804279177236");
            Role rogue = guild.getRoleById("1055200807227773018");
            Role cleric = guild.getRoleById("1055200997493977111");
            Role fighter = guild.getRoleById("1055201136119910521");
            Role bard = guild.getRoleById("1141379067359268906");
            Role warlock = guild.getRoleById("1141379724183085076");

            String memberEffectiveName = member.getEffectiveName();


            if (roleAdded.equals(barbarian)) {
                member.modifyNickname(memberEffectiveName + " [BARBARIAN]").queue();
            } else if (roleAdded.equals(ranger)) {
                member.modifyNickname(memberEffectiveName + " [RANGER]").queue();
            } else if (roleAdded.equals(wizard)) {
                member.modifyNickname(memberEffectiveName + " [WIZARD]").queue();
            } else if (roleAdded.equals(rogue)) {
                member.modifyNickname(memberEffectiveName + " [ROGUE]").queue();
            } else if (roleAdded.equals(cleric)) {
                member.modifyNickname(memberEffectiveName + " [CLERIC]").queue();
            } else if (roleAdded.equals(fighter)) {
                member.modifyNickname(memberEffectiveName + " [FIGHTER]").queue();
            }else if (roleAdded.equals(bard)) {
                member.modifyNickname(memberEffectiveName + " [BARD]").queue();
            }else if (roleAdded.equals(warlock)) {
                member.modifyNickname(memberEffectiveName + " [WARLOCK]").queue();
            }

        }, 1, TimeUnit.SECONDS); // Delay of 5 seconds

        executor.shutdown();
    }

}
