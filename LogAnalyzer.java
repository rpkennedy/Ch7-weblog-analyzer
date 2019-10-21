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
    // Use a LogfileReader to access the data.
    private LogfileReader reader;
    
    private int maximum;
    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer(String file)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
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
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
        for (int i = 0; i < hourCounts.length; i++)
            if (hourCounts[i] > maximum)
            maximum = hourCounts[i];
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
        int min = maximum;
        int quiet = 0;
        for (int i = 0; i < hourCounts.length; i++)
            if (hourCounts[i] > min){
            min = hourCounts[i];
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
