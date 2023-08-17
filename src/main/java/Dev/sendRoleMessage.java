package Dev;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;

public class sendRoleMessage extends ListenerAdapter {
    static Emoji barbarianEmoji = Emoji.fromUnicode("U+1FA93");
    static Emoji fighterEmoji = Emoji.fromUnicode("U+2694");
    static Emoji rangerEmoji = Emoji.fromUnicode("U+1F3F9");
    static Emoji wizardEmoji = Emoji.fromUnicode("U+1F52E");
    static Emoji rogueEmoji = Emoji.fromUnicode("U+1F5E1");
    static Emoji clericEmoji = Emoji.fromUnicode("U+1F6E1");
    static Emoji bardEmoji = Emoji.fromUnicode("U+1F3BB");
    static Emoji warlockEmoji = Emoji.fromUnicode("U+1FA78");
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getInteraction().getName().equals("sendrolemessage")){
            TextChannel textChannel = event.getChannel().asTextChannel();

            SelectMenu selectClass = SelectMenu.create("region-select")
                    .setPlaceholder("Wybierz swoj\u0105 klas\u0119!")
                    .setRequiredRange(1, 1)
                    .addOption("Barbarian", "barbarian", barbarianEmoji)
                    .addOption("Fighter", "fighter", fighterEmoji)
                    .addOption("Ranger", "ranger", rangerEmoji)
                    .addOption("Wizard", "wizard", wizardEmoji)
                    .addOption("Rogue", "rogue", rogueEmoji)
                    .addOption("Cleric", "cleric", clericEmoji)
                    .addOption("Bard", "bard", bardEmoji)
                    .addOption("Warlock", "warlock", warlockEmoji).build();

            textChannel.sendMessage("**Wybierz swoj\u0105 klas\u0119!**\n" +
                    "Witajcie Poszukiwacze Przyg\u00F3d! Czy faworyzujesz siebie jako okre\u015Blon\u0105 klas\u0119? Mo\u017cesz pochwali\u0107 si\u0119 swoim oddaniem tej klasie z odrobin\u0105 polotu! Zareaguj na emotk\u0119 powi\u0105zan\u0105 z wybran\u0105 klas\u0119, a otrzymasz t\u0119 rol\u0119! \n" +
                    "<@&1055200791759163402>: " + barbarianEmoji.getFormatted() + "\n" +
                    "<@&1055200800252624907>: " + fighterEmoji.getName() + "\n" +
                    "<@&1055200804279177236>: " + rangerEmoji.getFormatted() + "\n" +
                    "<@&1055200807227773018>: " + rogueEmoji.getFormatted() + "\n" +
                    "<@&1055200997493977111>: " + clericEmoji.getFormatted() + "\n" +
                    "<@&1055201136119910521>: " + wizardEmoji.getFormatted() + "\n" +
                    "<@&1141379067359268906>: " + bardEmoji.getFormatted() + "\n" +
                    "<@&1141379724183085076>: " + warlockEmoji.getFormatted()).addActionRow(selectClass).queue();
        }
    }
}
