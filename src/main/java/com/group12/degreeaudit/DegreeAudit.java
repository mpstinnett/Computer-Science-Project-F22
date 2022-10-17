package com.group12.degreeaudit;

import com.group12.degreeaudit.Administration.CourseList;
import com.group12.degreeaudit.Administration.DegreeList;

public class DegreeAudit
{
    public static void main(String args[])
    {
        String transcriptFilePath = "resources\\TSRPT_Sample2.txt";
        TranscriptScanner transcriptScanner = new TranscriptScanner(transcriptFilePath);
        transcriptScanner.scanTranscript();
        Student Luke_Skywalker = transcriptScanner.scanTranscript();
        CourseList temp = new CourseList("resources/CourseList.json");
        DegreeList temp2 = new DegreeList("resources/DegreeList.json");
    }
}
