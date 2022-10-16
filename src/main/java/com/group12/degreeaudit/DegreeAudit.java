package com.group12.degreeaudit;

import com.group12.degreeaudit.Administration.CourseList;

public class DegreeAudit
{
    public static void main(String args[])
    {
        String transcriptFilePath = "resources\\TSRPT_Sample2.txt";
        TranscriptScanner transcriptScanner = new TranscriptScanner(transcriptFilePath);
        transcriptScanner.scanTranscript();
        CourseList temp = new CourseList("resources/CourseList.json");
    }
}
