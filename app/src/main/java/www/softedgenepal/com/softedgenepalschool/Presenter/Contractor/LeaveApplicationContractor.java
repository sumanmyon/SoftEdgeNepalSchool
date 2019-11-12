package www.softedgenepal.com.softedgenepalschool.Presenter.Contractor;

import android.content.Context;

import java.util.List;
import java.util.Map;

import www.softedgenepal.com.softedgenepalschool.Model.Cache.LeaveApplication.LeaveApplicationDataCache;

public interface LeaveApplicationContractor {
    interface View{
        void setMessage(String message);
        interface Get{
            void setAllLeaveApplication(List<LeaveApplicationDataCache> leaveApplicationDataCacheList);
            void setMessageInTextView(String messageInTextView);
        }
    }

    interface Presenter{
        void setMessage(String message);
        Context getContext();
        interface Create{
            void createProgressBarVisibility();
            void createProgressBarInVisibility();
            void refresh();
        }
        interface Get{
            void getAllUserData();
            void getProgressBarVisibility();
            void getProgressBarInVisibility();
            void setMessageInTextView(String messageInTextView);

            void setAllLeaveApplication(List<LeaveApplicationDataCache> leaveApplicationDataCacheList);
        }
    }

    interface Model{
        void setMessage(String message);
        interface Create{
            void postUploadData(List<String> data);
            void refresh();
        }
        interface Get{
            void getAllUserLeaveData(Map<String, String> params, String studentId);
            void setMessageInTextView(String message);
        }

    }
}
