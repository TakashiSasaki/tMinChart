import android.os.Parcel;
import android.os.Parcelable;
import android.support.test.runner.AndroidJUnit4;
import android.util.Pair;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
//@SmallTest
public class LogHistoryAndroidUnitTest {

    public static final String TEST_STRING = "This is a string";
    public static final long TEST_LONG = 12345678L;
    private LogHistory mLogHistory;

    @Before
    public void createLogHistory() {
        mLogHistory = new LogHistory();
    }

    @Test
    public void logHistory_ParcelableWriteRead() {
        // Set up the Parcelable object to send and receive.
        mLogHistory.addEntry(TEST_STRING, TEST_LONG);

        // Write the data.
        Parcel parcel = Parcel.obtain();
        mLogHistory.writeToParcel(parcel, mLogHistory.describeContents());

        // After you're done with writing, you need to reset the parcel for reading.
        parcel.setDataPosition(0);

        // Read the data.
        LogHistory createdFromParcel;
        createdFromParcel = LogHistory.CREATOR.createFromParcel(parcel);
        List<LogEntry> createdFromParcelData = createdFromParcel.getData();

        // Verify that the received data is correct.
        assertThat(createdFromParcelData.size(), is(1));
        assertThat(createdFromParcelData.get(0).first, is(TEST_STRING));
        assertThat(createdFromParcelData.get(0).second, is(TEST_LONG));
    }
}

class LogEntry extends Pair<String, Long> implements Parcelable {

    public LogEntry(String first, Long second) {
        super(first, second);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.first);
        dest.writeLong(this.second);
    }

    public final static Parcelable.Creator<LogEntry> CREATOR = new Parcelable.Creator() {

        @Override
        public LogEntry createFromParcel(Parcel source) {
            LogEntry log_entry = new LogEntry(source.readString(), source.readLong());
            return log_entry;
        }

        @Override
        public LogEntry[] newArray(int size) {
            return new LogEntry[0];
        }
    };
}

class LogHistory implements Parcelable {
    ArrayList<LogEntry> data = new ArrayList<>();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(data);
    }

    public static final Parcelable.Creator<LogHistory> CREATOR = new Parcelable.Creator() {

        @Override
        public LogHistory createFromParcel(Parcel source) {
            LogHistory new_log_history = new LogHistory();
            new_log_history.data = source.readArrayList(LogEntry.class.getClassLoader());
            return new_log_history;
        }

        @Override
        public LogHistory[] newArray(int size) {
            return new LogHistory[0];
        }
    };

    public void addEntry(String testString, long testLong) {
        LogEntry log_entry = new LogEntry(testString, testLong);
        this.data.add(log_entry);
    }

    public ArrayList<LogEntry> getData() {
        return data;
    }
}