package remote.service.eso.myremoteapp.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by mawi7191 on 24.06.2016.
 */
public class TimeMesaure {
    private static final String TAG = "TimeMesaure";
    LinkedList<TimeValue> times;
    static TimeMesaure mTimeMesaure;

    public static TimeMesaure getInstance() {
        if (mTimeMesaure == null) {
            mTimeMesaure = new TimeMesaure();
        }
        return mTimeMesaure;
    }

    public TimeMesaure() {
        times = new LinkedList<TimeValue>();
    }

    public void mesureTimeStart(String TAG) {
        times.add(new TimeValue(System.currentTimeMillis(), TAG));
    }

    public void mesureTimeStop(String TAG) {
        times.add(new TimeValue(System.currentTimeMillis(), TAG, false));
    }

    public void dropResultInLog() {
        ArrayList<TAGContainer> TAGContainers = new ArrayList<>();
        String output = "";
        TAGContainer temp=null;
        //Iterate trough all Items with different Tags added while measurement
        for (TimeValue timeValue : times) {
            //Check the Tag
            temp=null;
            for (TAGContainer container:TAGContainers
                 ) {
                if(container.getTAG().equals(timeValue.getTAG())){
                    temp = container;
                }
            }
            if(temp == null){
                temp = new TAGContainer(timeValue.getTAG());
                TAGContainers.add(temp);
            }
//            temp = TAGContainers.get(TAGContainers.indexOf(timeValue.getTAG()));
            if (timeValue.isStarted()) {
                temp.addTimeStart(timeValue);
            } else {
                temp.addTimeStopped(timeValue);
            }

        }
        for (TAGContainer tagContainer : TAGContainers
                ) {
            Log.d(TAG, tagContainer.toString());
        }


    }

    class TimeValue {
        long time;
        String TAG;
        boolean started = true;

        public TimeValue(long time, String TAG) {
            this.time = time;
            this.TAG = TAG;
        }

        public TimeValue(long time, String TAG, boolean isStarted) {
            this.time = time;
            this.TAG = TAG;
            started = isStarted;
        }

        public boolean isStarted() {
            return started;
        }

        public void setStarted(boolean started) {
            this.started = started;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getTAG() {
            return TAG;
        }

        public void setTAG(String TAG) {
            this.TAG = TAG;
        }
    }
    class TAGContainer {
        private String TAG;
        private int avarage;
        private int median;
        private int geoAvarage;
        ArrayList<Long> times;
        TimeValue tempTimeValue;
        public TAGContainer(String TAG) {
            this.TAG = TAG;
            times = new ArrayList<Long>();
        }
        public void addTimeStart(TimeValue timeValue){
            tempTimeValue= timeValue;
        }
        public void addTimeStopped(TimeValue timeValue){
            times.add(timeValue.getTime()-tempTimeValue.getTime());
        }
        @Override
        public boolean equals(Object object)
        {
            boolean sameSame = false;
            if (object != null && object instanceof TAGContainer)
            {
                sameSame = this.TAG.equals(((TAGContainer) object).getTAG());
            }
            return sameSame;
        }

        @Override
        public String toString() {
            String output ="";
            long sum =0;
            long spanMin =Long.MAX_VALUE;
            long spanMax =Long.MIN_VALUE;
            output+="\n Measure for Tag "+TAG+"\n Times: ";
            for (long time:times
                    ) {
                output+= time+", ";
                sum+=time;
                if(time>spanMax){
                    spanMax = time;
                }
                if(time<spanMin){
                    spanMin = time;
                }
            }
            long avarage = sum/times.size();
            long meanDevation = 0;
            output+="\n Avarage: "+avarage+"ms\n";
            output+="Median: "+times.get(times.size()/2)+"ms\n";
            Collections.sort(times);
            for (long time:times
                    ) {
                meanDevation = Math.abs(sum-avarage);
            }
            meanDevation = meanDevation/times.size();
            output+="Mean deviation: "+meanDevation+"ms\n";
            output+="Span: "+(spanMax-spanMin)+"ms\n";
            return output;
        }

        public String getTAG() {
            return TAG;
        }
    }
}
