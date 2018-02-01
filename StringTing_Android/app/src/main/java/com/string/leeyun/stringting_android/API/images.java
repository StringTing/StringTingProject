package com.string.leeyun.stringting_android.API;

import java.util.List;

/**
 * Created by leeyun on 2017. 12. 30..
 */

public class images {

       private List<approved>approved;
       private List<in_review>in_review;

        public class approved{
            int id;
            String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

        }
        public class in_review{
            int id;
            String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }


    public List<images.approved> getApproved() {
        return approved;
    }

    public void setApproved(List<images.approved> approved) {
        this.approved = approved;
    }

    public List<images.in_review> getIn_review() {
        return in_review;
    }

    public void setIn_review(List<images.in_review> in_review) {
        this.in_review = in_review;
    }
}
