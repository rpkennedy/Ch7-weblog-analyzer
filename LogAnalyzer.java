/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    private int[] yearCounts;
    private int[] dayCounts;
    private int[] monthCounts;
    
    // Use a LogfileReader to access the data.
    private LogfileReader reader;
    
    private int maxhour;
    private int maxday;
    private int maxmonth;
    private int maxyear;
    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer(String file)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        dayCounts = new int[30];
        monthCounts = new int[12];
        // Create the reader to obtain the data.
        reader = new LogfileReader(file);
        
    }
    /**
     * Calculates total number of accesses throughout the LogFile
     */
    public int numberOfAccesses(){
        int total = 0;
        for (int i = 0; i < hourCounts.length; i++)
            while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            total++;
        }
        return total;
    }
    
    /**
     * Calculates total number of accesses for a month
     * @param month The integer value of the month
     */
    public int numberOfMonthlyAccesses(int month){
        int total = 0;
        while(reader.hasNext()) {
        LogEntry entry = reader.next();
            if (month == entry.getMonth()) 
                total++;
        }
        return total;
    }
    
    /**
     * Calculates total number of accesses for a month
     * @param month The integer value of the month
     */
    public int numberOfAverageMonth(){
        int total = 0;
        for(int i = 0; i < monthCounts.length; i++)
            while(reader.hasNext()) {
                LogEntry entry = reader.next();
                total++;
            }
        return total/12;
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
        for (int i = 0; i < hourCounts.length; i++)
            if (hourCounts[i] > maxhour)
            maxhour = hourCounts[i];
            
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int year = entry.getYear();
            yearCounts[year]++;
        }
        for (int i = 0; i < yearCounts.length; i++)
            if (yearCounts[i] > maxyear)
            maxyear = yearCounts[i];
        
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int month = entry.getMonth();
            monthCounts[month]++;
        }
        for (int i = 0; i < monthCounts.length; i++)
            if (monthCounts[i] > maxmonth)
            maxmonth = monthCounts[i];
        
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int day = entry.getDay();
            dayCounts[day]++;
        }
        for (int i = 0; i < dayCounts.length; i++)
            if (dayCounts[i] > maxday)
            maxday = dayCounts[i];
    }
    /**
     * Finds the hour with the highest amount of entries
     */
    public int busiestHour(){
        int max = 0;
        int busy = 0;
        for (int i = 0; i < hourCounts.length; i++)
            if (hourCounts[i] > max){
            max = hourCounts[i];
            busy = i;}
        return busy;
    }
    
    /**
     * Finds the day with the highest amount of entries
     */
    public int busiestDay(){
        int max = 0;
        int busy = 0;
        for (int i = 0; i < dayCounts.length; i++)
            if (dayCounts[i] > max){
            max = dayCounts[i];
            busy = i;}
        return busy;
    }
    
    /**
     * Finds the month with the highest amount of entries
     */
    public int busiestMonth(){
        int max = 0;
        int busy = 0;
        for (int i = 0; i < monthCounts.length; i++)
            if (monthCounts[i] > max){
            max = monthCounts[i];
            busy = i;}
        return busy;
    }
    
    /**
     * Finds the 2-hour period with the highest amount of entries
     */
    public int busiest2Hour(){
        int max = 0;
        int busy = 0;
        for (int i = 0; i < hourCounts.length; i++)
            if (hourCounts[i] > max){
            max = hourCounts[i] + hourCounts[i+1];
            busy = i;}
        return busy;
    }
    /**
     * Finds the hour with the lowest amount of entries
     */
    public int quietestHour(){
        int min = maxhour;
        int quiet = 0;
        for (int i = 0; i < hourCounts.length; i++)
            if (hourCounts[i] > min){
            min = hourCounts[i];
            quiet = i;}
        return quiet;
    }
    
    /**
     * Finds the day with the lowest amount of entries
     */
    public int quietestDay(){
        int min = maxday;
        int quiet = 0;
        for (int i = 0; i < dayCounts.length; i++)
            if (dayCounts[i] > min){
            min = dayCounts[i];
            quiet = i;}
        return quiet;
    }
    
    /**
     * Finds the month with the lowest amount of entries
     */
    public int quietestMonth(){
        int min = maxmonth;
        int quiet = 0;
        for (int i = 0; i < monthCounts.length; i++)
            if (monthCounts[i] > min){
            min = monthCounts[i];
            quiet = i;}
        return quiet;
    }
    
    /**
     * Finds the year with the lowest amount of entries
     */
    public int quietestYear(){
        int min = maxyear;
        int quiet = 0;
        for (int i = 0; i < yearCounts.length; i++)
            if (yearCounts[i] > min){
            min = yearCounts[i];
            quiet = i;}
        return quiet;
    }
    
    
    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
