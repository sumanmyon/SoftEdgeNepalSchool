package www.softedgenepal.com.softedgenepalschool.examplles;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import www.softedgenepal.com.softedgenepalschool.R;
import www.softedgenepal.com.softedgenepalschool.View.Activities.AssignmentActivity;

public class ExampleWebView {

    private void set(){
        WebView webView = null;// findViewById(R.id.webView);
        String r = "<p><span style=\"white-space:pre\">\t</span>var planes = [</p><p><span style=\"white-space:pre\">\t\t</span>[\"7C6B07\",-40.99497,174.50808]</p>";
        String a = "She walks in beauty, like the night, \nOf cloudless climes and starry skies\nAnd all that's best of dark and bright\nMeet in her aspect and her eyes...\");\n";
        String table = "<table>\n" + "   <tr>\n" + "      <td>Item A1</td>\n" + "      <td>Item A2</td>\n" + "      <td>Item A3</td>\n" + "   </tr>\n" + "   <tr>\n" + "      <td>Item B1</td>\n" + "      <td>Item B2</td>\n" + "      <td>(this is B3)</td>\n" + "   </tr>\n" + "   <tr>\n" + "      <td>Item C1</td>\n" + "      <td>-C2-</td>\n" + "      <td>*C3*</td>\n" + "   </tr>\n" + "   <tr>\n" + "      <td>Item number D1</td>\n" + "      <td>Item D2</td>\n" + "      <td>Item D3</td>\n" + "   </tr>\n" + "</table>";
        String table1 = "<table border=3 cellpadding=5>\n" + "   <tr>\n" + "      <td>Item A1</td>\n" + "      <td>Item A2</td>\n" + "      <th rowspan=3>Items A3, <br>\n" + "               B3, <br> C3 </th>\n" + "   </tr>\n" + "   <tr>\n" + "      <td>Item B1</td>\n" + "      <td>Item B2</td>\n" + "   </tr>\n" + "   <tr>\n" + "      <td rowspan=2>Items C1 and D1</td>\n" + "      <td>-C2-</td>\n" + "   </tr>\n" + "   <tr>\n" + "      <td>Item D2</td>\n" + "      <td>Item D3</td>\n" + "   </tr>\n" + "</table>";
        String table2 = "<table border=7 cellpadding=5>\n" + "   <tr>\n" + "      <th align=right>Item A1</th>\n" + "      <th>Item <i>A2</i></th>\n" + "      <th>Item A3</th>\n" + "   </tr>\n" + "   <tr>\n" + "      <td>Item B1</td>\n" + "      <td>Item B2</td>\n" + "      <td>(this is B3)</td>\n" + "   </tr>\n" + "   <tr>\n" + "      <td align=center>Item C1</td>\n" + "      <td>-C2-</td>\n" + "      <td>*C3*</td>\n" + "   </tr>\n" + "   <tr>\n" + "      <td>Item <b>number D1</b></td>\n" + "      <td>Item D2</td>\n" + "      <td align=right>Item D3</td>\n" + "   </tr>";
        //String image = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMTEhUSEhIVFRUVFxUVFRUVFRcVFRUVFRUWFxUWFRUYHSggGBolGxUVITEhJSkrLi4uFx8zODMtNygtLisBCgoKDg0OGhAQGy0lHyUtLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tKy0tLS0tLS0tLS0tLS0tLS0tLf/AABEIALEBHAMBIgACEQEDEQH/xAAcAAABBQEBAQAAAAAAAAAAAAAEAAIDBQYHAQj/xAA7EAABAwIDBQQIBAYDAQAAAAABAAIDBBEFITEGEkFRcSJhgZEHEyMyobHR8BRCweEWJDNygvFSU2JD/8QAGQEAAwEBAQAAAAAAAAAAAAAAAAECAwQF/8QAJBEAAgIBAwUBAQEBAAAAAAAAAAECEQMSITEEEyJBURRhMiP/2gAMAwEAAhEDEQA/AOHJ7AmBSxhAGk2QjvOxfSOzbbMaF87bGs9uxfR+z47ATF7L6NPTI09IYkkkkAJJJJAFNtFnG7ouF7St7Tuq7ntEfZu6FcQ2iHaKYgDBo+0PBdLwKPRc9wRvaC6TgLdEhmppWo5jUJShGsTAcxqfZIL0oAaWpjmqQLwoAiKYQpHhNcmBC8KMhTqJyQA7goXBEuUDkACTRoV7FYvQcuqBAEzEJNGrOQIWVqYypmjVfPErecIOZiYjPVkGSrjAtBVRZKtexAFNVU+WikpqXsjJFVY4Iqki7KgpnIwpohmowFNCM0yTY7DM9u1fRWADsBfP+wEV5R3L6CwMdkIAumherxq9SGJJJJACSSSQBR7Sn2bui4jj7u0Su17Un2bui4hjvvHxTAdgg7S6PgnDwXOcD1C6LgugSA1dKUZEEFTI1hTAnC9UbSnJAerwleOKaU6AaU1ycmFAHhURTzdeFiLGkyCQKB4sjTGF56oJOSGsbAXBCSR5q4MI5KCWAJa0PtPkp3BNdSOIuArERNabnNEStBFx4FJz+FRxXyZWtp3DUFAytWqnN8nC/MqpxDDxbeanGafISwtcGcqGKrnjzVzUNsqmrNtFfoxKusRdHIA1AVz815BLkkUzmbVPAM1CERAEEnRPRpFeRd9wtlmhcN9FzPaLu+HtyCmwDQvUkkxiSSSQAklA+rYMt4X5KN1ezmlY6Krav+mVxPH2He5fNdoxudr2kBc1x3BHPNxYJOaGoNlFgnvLomCnRYrDcMc11rgnkFuMLhLdRZNST4Bxa5NLTlHRlAU6NYVRJKCnkqMFIvSbGo2Pe6yiLikAvCptmqikJeBIr1iVlDrLxwUgTX2TEMTXOXj3r2Jt8/JJsr+noCilCnKHlSewIgkagJpDGbjNvEcu8Kwcq6pepZpF7j55wW5eaFhB909QhMMmG89h/KcuhzH08EdJINQlfsclWxm8VjsTZZ2tK11VBvXKzdfSGy1hLY58sN7Rl652a8j0UWINO+Fa0NJdgKfJizloRFPqhwiKdUB1T0W++u50OgXC/Rh73iu6Yf7oUgGLzeCFxCtEbSSc7E9AMyVga/bEvfuQnIEgHn3/AESlJR5KjBydI3dfi0UQu52fBozcfDgqF+KSz6dhvIa+JWYjDnOuXXJ1vf4nwWrw2nsFlrcmdHaUVbHQ0wGuZ70UG9ylEa93FXBIOGhMMTeIHkpXlDVU+6LosajZNHTQjP1bL9AiRu/8R5LPMrLkuvkE2lx5jpdwOBPHusbed0lIp4WaUsHIeSTQvIXXClcxXZk4oYV6kQonotlRiS3XhKCfMRommryS1Gigwx71EZbFBmuCHqKq4OalstY2XrXpj3qpocSDhYnMZFSTVYT1bE9tp0TTyXsOZt5o9hsFQUsu/K0XyHa8lfAoiwyRqkJNcnJpCbM0DyKrq1aShVlQEi4GVrqr1UzTfJ/Z8RmP1V5TuBF1ndsYiYXOaO0wh4t3HP4ErSbOU942ufqQDbgFC5o3mvGxSwPeLMHidP3VXWUBYLHNbAhVWJsuCqowTs5pjFCL3CfROswK6rsLe4EtFwqZtI4ZEFOLa5JyQi90cqZSE6KxocHe46FabBMEOpC2FHg4sLBbHJY70dYWYteK6hPiLYYi9x0F/oFlsFp9xZX0qbU+q3IBnvDfdnb3TkPOyHwC5AfSFtk8uMTHe+O3Y6NByb81X7ME+8de/wC/gsCypdNKXHMnNb/ASGgXJvxN+mX3/rkzcnodOvFm6wplyO+339VtqeOwCyez8V90rYMGSrEticvNDk1yddQyPWjMiOQqsxd3YKPe5VtebtPRSzSC3M4+R34YkHtPda/Lffug+F/gsnszV0jHuqKyobE57iIWF2bYm3Y0kZ2vunPQrdmk3oty2YsR1abj5LhuKUrqaWWH1faL3ODnE/03EbmRyIADgcr3uL5KsSS5H1E5JeP0+i8CrmvA3Hh7S27XNILXDgQRqtAzMLmfompHMo43HLfL3Aa2a6QkEd1gD/kulNdYJr2ZvdL6NeEM8qWWRCyyhS2VEildnkoiVDJOLphnHNRZuosBxamcBvR5ni3n0QccVQ7/AOR8SFd728FNFU7uRSrc17jjGqsyk9NVxv32x3H5hvDMd3eharHS3JwLTxDlun1LSFTYlhrJfeaD1UuPxl486b80VGx2Lh8zwDchot5reQPuuc0eCNpphJGLE9ki+oNv1AXRaGOzRfXj1V474Zl1bi3qiE2ScErrxzlq2cQLMqypVjO+yrKp9goZrBFDjZ9nJ/a75K22el3o2HuHyWZ2vqwynkdzFh/kQ39Vb7GS3hZ0ChPyOiUf+ZqihKuMEIsJkrLqzk4ZUULw2Tdd7r8uh4fRTVWDNLiQAh69mSkp8UJbnqMj1C1g1wxTi3uihocPDeCuoIUHE9GRvKo5QmR4a0k8AvnLbbF/xFXI8e6HFjejTb912bbvFDDSSPBz3bDqcgvnq9zmkwiXeCRNJzJudAFu8Bh3nDM7ozz+8/vmucUUpDm2yXRMCrRYXNtCenyXJl5PS6d3E6ns2y9j92WkVBszLdo6eSv1pj/yZZb1DHqORSOHFRPVtEWCvJHFDvbcImoAQwUmiY1sSqMZ2Rpau34iLe3b2c1xa7PUXB0PJX8bwiowE0Jv6Mw6jZE1rWNDWtAa1oFg1oyAA4BFSPOgF/0TmAWSnIDU2qJTtlfLN2t3eF9DnoVHNAbKqbTH8U6Xf7L2hpZbLeacng87WFu5X0jxZZp3ybyjpaSZhNpsYNOLm5uQB1JyCWCTyzWLrMb/AOjn5Ki25xn+bji3RZhcTvAG5FgNepVjTYrZvu36KVG3Z6EYPR/TeUdOLc+9R1sOR5FZqhxUuHZu117C3HMW6q+pa4yMJIF2ktNtDYA3+Kp1wc2TBOD1GXGLuZI6N5zabfRXMGIgjVZTbameJGSxtLibtcB3Zgr3C2zkf0yOuSyTa2NZQUlZqqeYOlaMufktdTnJYLBKOUTguAAsePFbyAZLaDOPPSpIlCY8p6ikKswSBagqpq3ZI+odmqaukAChm8ImM9IUv8uR3tJ8HBX+wcl4GdFkto5hMHsv7xDR13h+632ytCIo2MHADzUR3ZtN+DRpY0569jC9kW3o4WVeIMWdqMnHgtLW6LPVTO0c1LNIqxU8t1YRvuFXwNGl8ka2MWsug4Dnnpfrz6lkYOrs+gBv+i5G3VdP9L8VjEf7v0XNIRmoLSDqZvZB77ea2WzcQcW8yfL6n765anh3h2fsro2wGD57zuBC58m+x39P4ps6dgMO6wdFdNegKNtgjS9axVIxk7Z443TCvXSKKSRD3BIiqnKukqc7Iid97qmnlAfuk65qGbQiWn4kAXXkNfna6r5nttdxsE7C54C8xXO/YnQ2AHfoD3HNNBSq6Lv8UbKEV+9duhCKihFlHU0N825HmhpiTijJ4RPO6pmdK0tY2QtjBORaLtBA77b3+S001TkhoqFwdvPOmgGihxV1goSaRtKSnJHG9rKp8tbZ35S7Pq7Tw3VrMIAEY4nzWaxaAGtLnaENPxN/ktNSYpExtg1pPmlGSR6LjqTkEm7G7ziciLd2v3953Gz1SSy5/M4u8/8ASzkxlqCMiG3+C01DDuN6JXbsnNKOjQhuJzAG50yT6XEGnIKpxZr5n+pjzccz3C+pWhwjZ71bRvG542+qlW3sc01GMdw/DHXdcrQR6KqioGjhbvub/NFxBzdDfuOvmt4nFNp8BhCHlXoqBpoeR+80PPImQrAawrI7U4l6uMnjoOp0Wnr5bArj+3eLb0oiByZmep0+HzWb32OvHsrYsHJlqIxy7R+S7FhjbNC45sM685PcF2jDW9keCcdgyPwRaQDJKUqSNRylaHGVlW7VUk5zVvVP1VPM7NSbRQAye2SMhqctVXscCnOPgtzzjHell5dHGeRXNIG5hdB9I73FjRw4ffisDAoZcS8w1ml9DZdH2ZqdzdGl+XwXPcFNyM+436/utphjrOYeWXwXJNtM9PElpOqUk/ZCJMyp8MnuwIt8tlunsYadwyWTkhXyoaSoUbpErKUSR71k6yrvVBo5H4LQyvyOazIi/mA7r1z/ANKJPg3xrkp9pNpDHUMja1z+1uhjMnE2BNuRNwN62Q3labO7bskmjpn0rYXOuWGN2+Pcc4h+80OBLc7m9+K57t/C5tUXAaONraneaD9fIqf0cYZJJVsks/djuS5183OYWBovrZpJ6NtxC6YuoHDkUnlS+H0Fh0lx0y8v2srHdyVThg17yD8ArXfUFyW+wPMwKtr6MOFlYzuuEI8qGXFM5ttDsY58okaSRbtNGRIBvr4lXOE7OwtYBu6fNa2wUMjW6hTRv3G9gGKkY3ggcWq2xtJHgBqScgB3k2VpUygNuVU4LEJ5XSuzZESGf3Adp3hew8Un8Q4capeg/ZbDvVtc99jI87zzyP5WDuA+NzxWkiFwhqSAAE881LSvJF7cT8DZXFUc+Wbm2wj1a8LU7fumF3ZzVGQNO4HI/wCvognT27LuGYPMfVMrqnNZzHMV3Wlw1bn14W8b+aTfs3hBt0FY1WWafu/ILA0uxrp5C95N3EuPjmtThcT6hwc4EDg39StnRUAaFkrbs3m1BUc8o9mTRyiQe44Bp/8AJvken7LoGHyiwRk1K1wLSAb63VI6gmhPsx6xnAXAe3uzycPI9VdNMjUpquDTiTJRuKDo6hxHaY4dR9FPI/JXZyuNOisrVVSaqxrXaqkmkzU2bLgz8dfewFvO9kQ2r4Xv3cVmmVgyuW68WbuffyPin/iQHEA26HMeBW55pFtm8Oi6Ef7XPo9bLV49Ukssb66/eqyDzY3UtFplvhMtn/fkVuKGovY8RY9e75+a5zTzZ34q7osSI4rDJBs7cGRVTOw4TV9kAeHJHSVXNYTZ/GBaxWjdWtcBmlGWxs47lqH8V4Z87XVV+KtxUZqxzTsNJcOeLKpmPtQR9lNbVXUUkLiQRzUy4LhSZDtFgDajPjYA+GbSO8HTqVebH4F6pg3szlc/PwyT6GMlX9NHugLRcGeSRYRtsEnShDukNkPI5MyjElklzUEkg5qGSRDSzqWdEYhElSOaCnrbISqrAAc1SVuIZLJyNoYgrGMVs05qDYTGGmne0HtNkeXDq/e+RWF2ixiwOfcqvYvaE08rg65jk97uPPyVQi2rJy5Ixah9PocV/s7jWysIHtDQByXKpcUcxtw8uicMiMy2/PuRuH7cxvG7ezxkRe469ytSMJYfh0fDm++8n3nZdzW9n5gnxQmM4g2JhcTkNfNZnCtrG+qLSe0xxBtycS5p8jbwKpNrsUM0RjFu0LEdxQ5qthxwPXvxY7E9rY3Ehrr8Ms/JBUkT5nAuGWoH6lCYBgIFiQtjS04bbJRTfJ0OSjsi5wam3G6K6YFXUjsvJFtmWqRwztuwhJQOmTTOmTTCMkLUvCgnqctVVz1pU2UoDK+fVUMtTmVPVuLstBz7u5Vr22Kk1SJIMAZbI91v2Un8MQnMtFxobZhHQko6IZLrPKMxUbEQvByt0Jt1A4KvHo0hP53ea6DExSMjSaHZztnowjBuJHIhvo0Z/wBrvh9F0BrUYyEKWkUptHOofR65nuzHxAVizZKW39QHwK3AjCeGqe3E1/RNezDfwnN/2DySbsnNfN7eua3VkrI7UR/pyfTFx7MTDVzSrWlwl41sr9JPQhPqJsAioyEW1hUq9AT0k92QO6Anio30juYRoaU8BLSilnkVL6B54hBT4RKdC1aTdSsk4ItdVNcGIqNmJz+Zt1V1mxNS8WD2DzXSXAKO6ntRK/blOKVXomrHuuZogOA7SusJ9FAY32kgc7mMh5LqN0lppVUYd13fswX8AW9yXd7tW+SDHoxb6wv9ac+61ui6Qkp7aNP1ZPpgm+j4NNxKQe7VHQ7Hbv57/fNa+y9R20H6sn0zsWCOZpZEMw51syFcpWT0In9EwJkLgndpFkJu6jSLvSB2k8UO8HmjixCSNIRpQu9IGdCTxUD6QcyjSo3p6UJ5Z/SrloW8b+aBmpGX934n6q2kB4oKS19EKKE8svpDE5HwOyVNTOVlA9WZlk2S1kQEAxyMjKBhIbkESxCRvRUZUkkoSKQKSZQk7gmpzSEgPLJ7AvQV6gDwNTgEkgUAOAXpK8Ca5yAPSUxz0xz01AHt0l4nBhTAantZdOEalASAh9UnNZZSWXiAI3hRqYqMtKAGJL0hNQB6F4V6vCgYlFIVISoZnZHogQPKc1C9ekqOQpiBJnXUFlK8od70CZUwKxhSSVMbDo0bEkkkBKjI0kkgZME5JJAI8SSSSKHxp6SSBCSSSQAlHIkkgBjV6EkkAejVEBJJACTgkkgDwrxJJADXJJJIAiempJIAS8XqSBkb1BLxSSQQCFRSJJKgYG5Bv1SSQM//2Q==";


        String compile = table2;
        String[] strings = new String[]{"\b","\t","\n","\f","\r"};//,"\\\'","\\\"", "\\\\"};
        for (String s:strings) {
            compile = compile.replaceAll(s, "");//Matcher.quoteReplacement(""));
            Log.d("TAG_WebView",compile);
        }
        //webView.loadData(compile, "text/html", null);

       // webView.setWebViewClient(new AssignmentActivity.MyBrowser());
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.requestFocus();
        webView.loadData(compile,"text/html", null);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //view.loadUrl(url);
            view.loadData(url,"text/html", null);
            Log.d("TAG_WebView","hahaha\t"+url);
            return true;
        }
    }
}
