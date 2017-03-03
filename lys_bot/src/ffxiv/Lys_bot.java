package ffxiv; 

import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;

import java.util.List;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class Lys_bot extends ListenerAdapter{
	Member memberRole; 
	public static void main(String[] args) {
		try {
			new JDABuilder(AccountType.BOT)
			.setToken("Mjg2OTA3NjU1MjM3MjcxNTUy.C5nlJA.8SYQDkro428pmEFZrtI4znyZJkU") // Use token provided as JVM argument
			.addListener(new Lys_bot()) // Register new MusicBot instance as EventListener
			.buildAsync();
		} catch (LoginException | IllegalArgumentException | RateLimitedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Build JDA - connect to discord
	}
		@Override
		public void onMessageReceived(MessageReceivedEvent event)
	    {
	        //These are provided with every event in JDA
	        JDA jda = event.getJDA();                       //JDA, the core of the api.
	        long responseNumber = event.getResponseNumber();//The amount of discord events that JDA has received since the last reconnect.

	        //Event specific information
	        User author = event.getAuthor();                  //The user that sent the message
	        Message message = event.getMessage();           //The message that was received.
	        MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.
	                                                        //  This could be a TextChannel, PrivateChannel, or Group!

	        String msg = message.getContent();              //This returns a human readable version of the Message. Similar to
	                                                        // what you would see in the client.
	        String[] msg2 = msg.split(" ");
	     //   String msgpart = msg2[0];
	     //   String fname = msg2[2];
	     //   String lname = msg2[3];
	        Member member = event.getMember();          //This Member that sent the message. Contains Guild specific information about the User!
	        Guild guild = event.getGuild();             //The Guild that this message was sent in. (note, in the API, Guilds are Servers)
	        GuildController guildcontroller = guild.getController();
	        List<Role> roles = guild.getRoles();
	        
	        
	        boolean bot = author.isBot();                     //This boolean is useful to determine if the User that
	                                                        // sent the Message is a BOT or not!

	        if (event.isFromType(ChannelType.TEXT))         //If this message was sent to a Guild TextChannel
	        {
	            //Because we now know that this message was sent in a Guild, we can do guild specific things
	            // Note, if you don't check the ChannelType before using these methods, they might return null due
	            // the message possibly not being from a Guild!

	            
	            TextChannel textChannel = event.getTextChannel(); //The TextChannel that this message was sent to.
	            

	            String name = member.getEffectiveName();    //This will either use the Member's nickname if they have one,
	                                                        // otherwise it will default to their username. (User#getName())

	            System.out.printf("(%s)[%s]<%s>: %s\n", guild.getName(), textChannel.getName(), name, msg);
	        }
	        else if (event.isFromType(ChannelType.PRIVATE)) //If this message was sent to a PrivateChannel
	        {
	            //The message was sent in a PrivateChannel.
	            //In this example we don't directly use the privateChannel, however, be sure, there are uses for it!
	            PrivateChannel privateChannel = event.getPrivateChannel();

	            System.out.printf("[PRIV]<%s>: %s\n", author.getName(), msg);
	        }
	        else if (event.isFromType(ChannelType.GROUP))   //If this message was sent to a Group. This is CLIENT only!
	        {
	            //The message was sent in a Group. It should be noted that Groups are CLIENT only.
	            Group group = event.getGroup();
	            String groupName = group.getName() != null ? group.getName() : "";  //A group name can be null due to it being unnamed.

	            System.out.printf("[GRP: %s]<%s>: %s\n", groupName, author.getName(), msg);
	        }


	        //Now that you have a grasp on the things that you might see in an event, specifically MessageReceivedEvent,
	        // we will look at sending / responding to messages!
	        //This will be an extremely simplified example of command processing.

	        //Remember, in all of these .equals checks it is actually comparing
	        // message.getContent().equals, which is comparing a string to a string.
	        // If you did message.equals() it will fail because you would be comparing a Message to a String!
	        if (msg.equals("!pong"))
	        {
	            //This will send a message, "pong!", by constructing a RestAction and "queueing" the action with the Requester.
	            // By calling queue(), we send the Request to the Requester which will send it to discord. Using queue() or any
	            // of its different forms will handle ratelimiting for you automatically!

	            channel.sendMessage("!ping").queue();
	        }
	        if (msg.equals("!help"))
	        {
	            //This will send a message, "pong!", by constructing a RestAction and "queueing" the action with the Requester.
	            // By calling queue(), we send the Request to the Requester which will send it to discord. Using queue() or any
	            // of its different forms will handle ratelimiting for you automatically!

	            channel.sendMessage("!ping Pong!").queue();
	        }
	        if (msg2[0].equals("!iam"))
	        {
	        	guildcontroller.setNickname(member, msg2[2] + " " + msg2[3]).queue();
	            channel.sendMessage("Nickname Updated").queue();
	            memberRole = member;
	            
	        }
	        if (msg.equals("Character updated, Kupo!"))
	        {
	        	guildcontroller.addRolesToMember(memberRole, roles.get(2)).queue();
	        	channel.sendMessage("Bienvenue LYS!")l.queue();
	        }
	}

}
