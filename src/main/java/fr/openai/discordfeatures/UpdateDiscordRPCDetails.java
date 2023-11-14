package fr.openai.discordfeatures;

public class UpdateDiscordRPCDetails {
    private final DiscordRPC discordRPC = new DiscordRPC();
    protected DiscordDetails discordDetails = new DiscordDetails();

    public void updateDiscordRPCDetailsScary() {
        String newDetails = discordDetails.getRandomScaryPhrase();
        discordRPC.updateRPCDetails(newDetails);
    }

}
