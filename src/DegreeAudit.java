
public class DegreeAudit {
    static final String transcriptFilePath = "src\\TSRPT_Sample2.txt";
    
    public static void main(String args[]) {
        TranscriptScanner transcriptScanner = new TranscriptScanner(transcriptFilePath);
        transcriptScanner.scanTranscript();
    }
}