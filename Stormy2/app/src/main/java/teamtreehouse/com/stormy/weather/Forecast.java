package teamtreehouse.com.stormy.weather;

public class Forecast {

    private Current mCurrent;
    private Hour[] mHourlyForcast;
    private Day[] mDailyForcast;

    public Current getCurrent() {
        return mCurrent;
    }

    public void setCurrent(Current current) {
        mCurrent = current;
    }

    public Hour[] getHourlyForcast() {
        return mHourlyForcast;
    }

    public void setHourlyForcast(Hour[] hourlyForcast) {
        mHourlyForcast = hourlyForcast;
    }

    public Day[] getDailyForcast() {
        return mDailyForcast;
    }

    public void setDailyForcast(Day[] dailyForcast) {
        mDailyForcast = dailyForcast;
    }
}
