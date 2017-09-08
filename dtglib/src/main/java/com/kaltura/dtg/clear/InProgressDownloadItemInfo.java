package com.kaltura.dtg.clear;

import java.util.ArrayList;
import java.util.List;


public class InProgressDownloadItemInfo {

    private long totalBytes;
    private List<String> completedDownloadItemTasks;
    private int numOfChunksInItem;
    private DefaultDownloadItem defaultDownloadItem;
    private int lastReportedProgressPrecent;

    public InProgressDownloadItemInfo(DefaultDownloadItem defaultDownloadItem, int numOfChunksInItem) {
        this.numOfChunksInItem = numOfChunksInItem;
        this.defaultDownloadItem = defaultDownloadItem;
        this.totalBytes = 0;
        this.completedDownloadItemTasks = new ArrayList<>();
        this.lastReportedProgressPrecent = -1;
    }

    public synchronized long getTotalBytes() {
        return totalBytes;
    }

    public synchronized void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
    }

    public synchronized void addCompletedTaskIdToList(String taskId) {
        this.completedDownloadItemTasks.add(taskId);
    }

    public synchronized List<String> getCompletedDownloadItemTasks() {
        return completedDownloadItemTasks;
    }

    public int getNumOfChunksInItem() {
        return numOfChunksInItem;
    }

    public DefaultDownloadItem getDefaultDownloadItem() {
        return defaultDownloadItem;
    }

    public synchronized int getPendingCount() {
        return numOfChunksInItem - completedDownloadItemTasks.size();
    }

    public synchronized int getLastReportedProgressPrecent() {
        return lastReportedProgressPrecent;
    }

    public synchronized void setLastReportedProgressPrecent(int lastReportedProgressPrecent) {
        this.lastReportedProgressPrecent = lastReportedProgressPrecent;
    }

    public boolean shouldSendProgressUpdate() {
        int  newProgress = (int)(100f * totalBytes / defaultDownloadItem.getEstimatedSizeBytes());
        boolean shouldSendProgressUpdate =  (newProgress != lastReportedProgressPrecent);
        if (shouldSendProgressUpdate) {
            lastReportedProgressPrecent = newProgress;
        }
        return shouldSendProgressUpdate;
    }
}
