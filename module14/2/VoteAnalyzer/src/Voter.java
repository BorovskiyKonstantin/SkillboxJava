import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Voter implements Comparable<Voter> {
    private byte[] name;
    private Date birthDay;
    private int voteCount;

    public Voter(String name, Date birthDay) {
        this.name = name.getBytes();
        this.birthDay = birthDay;
        this.voteCount = 0;
    }

    @Override
    public boolean equals(Object obj) {
        Voter voter = (Voter) obj;
        return Arrays.equals(name, voter.name) && birthDay.equals(voter.birthDay);
    }

    @Override
    public int hashCode() {
        long code = Arrays.hashCode(name) + birthDay.hashCode();
        while (code > Integer.MAX_VALUE) {
            code = code / 10;
        }
        return (int) code;
    }

    public String toString() {
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
        return new String(name) + " (" + dayFormat.format(birthDay) + ")";
    }

    public String getName() {
        return new String(name);
    }

    public Date getBirthDay() {
        return birthDay;
    }

    @Override
    public int compareTo(Voter o) {
        if (Arrays.compare(name, o.name) == 0) {
            return birthDay.compareTo(o.birthDay);
        }
        return Arrays.compare(name, o.name);
    }

    public void addVote() {
        voteCount++;
    }

    public int getVoteCount() {
        return voteCount;
    }
}
