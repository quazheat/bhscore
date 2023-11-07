package fr.openai.discordfeatures;

public class UpdateDiscordRPCDetails {
    private final DiscordRPC discordRPC = new DiscordRPC();

    public void updateDiscordRPCDetailsScary() {
        DiscordDetails discordDetails = new DiscordDetails();
        String newDetails = discordDetails.getRandomScaryPhrase();
        DiscordRPC.updateRPCDetails(newDetails);
        discordRPC.updateRPC();
    }

}
