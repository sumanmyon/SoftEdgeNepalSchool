package www.softedgenepal.com.softedgenepalschool.Model.Cache;

import java.io.Serializable;

import www.softedgenepal.com.softedgenepalschool.Model.Repositroy.ReportCardModel;

public class ReportCardCache implements Serializable {
    public String ExamNameEng;
    public String ExamCode;

    public ReportCardCache(String examNameEng, String examCode) {
        ExamNameEng = examNameEng;
        ExamCode = examCode;
    }
}
