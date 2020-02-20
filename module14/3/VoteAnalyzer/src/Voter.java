import java.text.SimpleDateFormat;
import java.util.Date;

public class Voter implements Comparable<Voter> {
    private String name;
    private Date birthDay;
    private int voteCount;

    public Voter(String name, Date birthDay) {
        this.name = name;
        this.birthDay = birthDay;
        this.voteCount = 0;
    }

    @Override
    public boolean equals(Object obj) {
        Voter voter = (Voter) obj;
        return name.equals(voter.name) && birthDay.equals(voter.birthDay);
    }

    @Override
    public int hashCode() {
        long code = name.hashCode() + birthDay.hashCode();
        while (code > Integer.MAX_VALUE) {
            code = code / 10;
        }
        return (int) code;
    }

    public String toString() {
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
        return name + " (" + dayFormat.format(birthDay) + ")";
    }

    public String getName() {
        return name;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    @Override
    public int compareTo(Voter o) {
        if (name.compareTo(o.name) == 0) {
            return birthDay.compareTo(o.birthDay);
        }
        return name.compareTo(o.name);
    }

    public void addVote() {
        voteCount++;
    }

    public int getVoteCount() {
        return voteCount;
    }
}
