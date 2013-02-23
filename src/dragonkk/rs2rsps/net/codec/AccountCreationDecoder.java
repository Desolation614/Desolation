package dragonkk.rs2rsps.net.codec;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import dragonkk.rs2rsps.io.InStream;
import dragonkk.rs2rsps.io.OutStream;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.Constants;
import dragonkk.rs2rsps.util.Misc;
import dragonkk.rs2rsps.util.Serializer;

public class AccountCreationDecoder {

    private static boolean BadName(String name) {
        return name.contains("nerd") || name.contains("fuck") || name.contains("gay") || name.contains("motherf");
    }

    private static List<String> SuggestedNames(String name) {
        List<String> Names = new ArrayList<String>();
        for (byte i = 0; i < 5; i++) {
            String newName = name + Misc.random(5000);
            if (AccountExists(newName) || Names.contains(newName))
                continue;
            Names.add(newName);
        }
        return Names;
    }

    private static boolean AccountExists(String name) {
        return new File("Data/savedgames/" + name + ".ser").exists();

    }

    /*
     *Where i say noob hacker means only way to that code part be called is by hacking
      */
    public static void decode(ConnectionHandler c, InStream in) {
        if (c.getConnectionStage() == Constants.CHECK_ACC_NAME) { //protocol id 21 =)
            if (in.remaining() < 8) {
                c.setConnectionStage(Constants.DISCONNECT);//dont send fake packets ^^
                return;
            }
            String PlayerName = Misc.longToString(in.readLong());
            @SuppressWarnings("unused")
            int PacketSize = in.readUnsignedByte();
            byte OpCode = 0;
            if (PlayerName == null) {
                OpCode = 22;
            } else if (BadName(PlayerName)) {
                //will say name is invalid or already taken and it couldnt find any suggestion =)
                OpCode = 21;
                OutStream outstream = new OutStream();
                outstream.writeByte(OpCode);
                outstream.writeByte(0);
                c.write(outstream);
                c.setConnectionStage(Constants.DISCONNECT);
                return;
            } else if (AccountExists(PlayerName)) {
                OpCode = 21;
                OutStream outstream = new OutStream();
                List<String> SuggestedName = SuggestedNames(PlayerName);
                outstream.writeByte(OpCode);
                if (SuggestedName == null)
                    outstream.writeByte(0); //no suggestions
                else {
                    outstream.writeByte(SuggestedName.size()); //sends name suggestions
                    for (String newname : SuggestedName)
                        outstream.writeLong(Misc.stringToLong(newname));
                }
                c.write(outstream);
                c.setConnectionStage(Constants.DISCONNECT);
                return;
            } else {
                OpCode = 2;
            }

            OutStream outstream = new OutStream();
            outstream.writeByte(OpCode);
            c.write(outstream);
            c.setConnectionStage(Constants.DISCONNECT);
        } else if (c.getConnectionStage() == Constants.CHECK_ACC_COUNTRY) { //protocol id 22 =)
            if (in.remaining() < 6) { //dont send fake packets ^^
                c.setConnectionStage(Constants.DISCONNECT);
                return;
            }
            int birthDay = in.readUnsignedByte();
            int birthMonth = in.readUnsignedByte();
            int birthYear = in.readShort();
            int Country = in.readShort();
            Calendar Birth = new GregorianCalendar(birthYear, birthMonth, birthDay);
            Calendar ThisDate = new GregorianCalendar();
            byte OpCode = 0;
            if (Birth.after(ThisDate)) {
                OpCode = 11; //invalid birthday as it is on future
            } else if (Birth.get(Calendar.YEAR) == ThisDate.get(Calendar.YEAR)) {
                OpCode = 12; //invalid birthday as it is this year
            } else if (Birth.get(Calendar.YEAR) - ThisDate.get(Calendar.YEAR) == -1) {
                OpCode = 13; //invalid birthday as it was last year
            } else if (Birth.get(Calendar.YEAR) - ThisDate.get(Calendar.YEAR) <= -100 || Birth.get(Calendar.YEAR) - ThisDate.get(ThisDate.YEAR) >= -5) {
                OpCode = 10; //invalid birth pls try other...
            } else if (Country < 0 || Country > 255) {
                OpCode = 14; //invalid country - noob hacker
            } else if (Birth.get(Calendar.YEAR) - ThisDate.get(Calendar.YEAR) >= -12) {
                OpCode = 15; //will send to parents email part
            } else {
                OpCode = 2; //will send to optional email part
            }
            OutStream outstream = new OutStream();
            outstream.writeByte(OpCode);
            c.write(outstream);
            c.setConnectionStage(Constants.DISCONNECT);
        } else if (c.getConnectionStage() == Constants.MAKE_ACC) { //protocol id 23 =)
            byte OpCode = 0;
            int length = in.readShort();
            /*if (in.remaining() != length) { //dont send fake packets ^^
               c.setConnectionStage(Constants.DISCONNECT);
               return;
           }*/
            int revision = in.readShort();
            int[] is = new int[4]; //TODO used for email, also its random key =)
            in.skip(28);
            in.readUnsignedByte();
            int settings = in.readShort();
            String PlayerName = Misc.formatPlayerNameForProtocol(Misc.longToString(in.readLong()));
            is[0] = in.readInt();
            String password = in.readRS2String();
            is[1] = in.readInt();
            @SuppressWarnings("unused")
            int unsure = in.readShort();
            int birthDay = in.readUnsignedByte();
            int birthMonth = in.readUnsignedByte();
            is[2] = in.readInt();
            int birthYear = in.readShort();
            int Country = in.readShort();
            is[3] = in.readInt();
            /*OutStream outstream1 = new OutStream(8);
           outstream1.writeInt(0);
           outstream1.writeInt(0);
           p.write(outstream1);
           in.readInt();
           in.readInt();*/
            //String email = in.readRS2String();
            String email = "TODO@mail.com"; //TODO uses hufman or something like that
            Calendar Birth = new GregorianCalendar(birthYear, birthMonth, birthDay);
            Calendar ThisDate = new GregorianCalendar();

            if (revision != Constants.REVISION) {
                OpCode = 37; //pls reload page your client is outdated
            } else if (BadName(PlayerName) || AccountExists(PlayerName)) {
                OpCode = 20; //invalid name... - noob hacker
            } else if (Birth.after(ThisDate)) {
                OpCode = 11; //invalid birthday as it is on future
            } else if (Birth.get(Calendar.YEAR) == ThisDate.get(Calendar.YEAR)) {
                OpCode = 12; //invalid birthday as it is this year
            } else if (Birth.get(Calendar.YEAR) - ThisDate.get(Calendar.YEAR) == -1) {
                OpCode = 13; //invalid birthday as it was last year
            } else if (Birth.get(Calendar.YEAR) - ThisDate.get(Calendar.YEAR) <= -100 || Birth.get(Calendar.YEAR) - ThisDate.get(Calendar.YEAR) >= -5) {
                OpCode = 10; //invalid birth pls try other...
            } else if (Country < 0 || Country > 255) {
                OpCode = 14; //invalid country - noob hacker
            } else if (password == null || password.length() < 5) {
                OpCode = 30; //invalid pass
            } else if (password.equalsIgnoreCase("qwerty") || password.equalsIgnoreCase("12345") || password.equalsIgnoreCase("09876") || password.equalsIgnoreCase("lol123") || password.equalsIgnoreCase("killer")) {
                OpCode = 32; //easy passwords
            } else if (password.equalsIgnoreCase(PlayerName)) {
                OpCode = 34; //pass to smiliar to my name
            } else if (email == null
                    || !email.contains("@")
                    || email.length() < 5
                    || (!email.contains(".com")
                    && !email.contains(".net")
                    && !email.contains(".org")
                    && !email.contains(".pt")
                    && !email.contains(".info")
                    && !email.contains(".live")
                    && !email.contains(".uk"))
                    && !email.equals("")) {
                OpCode = 41; //invalid email
            } else if (settings > 3 || settings < 0) {
                OpCode = 38; //invalid settings - noob hacker
            } else {
                OpCode = 2; //will send to finish part
            }
            OutStream outstream = new OutStream();
            outstream.writeByte(OpCode);
            c.write(outstream);
            c.setConnectionStage(Constants.DISCONNECT);
            if (OpCode == 2) { //All fine so lets start registering acc
                Player Account = new Player(PlayerName, password, Birth, ThisDate, (short) Country, email, (byte) settings);
                synchronized (Account) {
                    Serializer.SaveAccount(Account);
                }
            }
        } else {
            c.setConnectionStage(Constants.DISCONNECT); //cant happen but lets be sure
        }
    }
}
