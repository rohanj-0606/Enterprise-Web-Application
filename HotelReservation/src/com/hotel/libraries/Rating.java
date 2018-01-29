package com.hotel.libraries;

public class Rating {
        String id;
        String rating;
        String review;
        
        public Rating(String id, String rating, String review) {
            this.id = id;
            this.rating = rating;
            this.review = review;
                    
        }
        
        public String getId(){
            return id;
        }
        
        public String getRating(){
            return rating;
        }
        
        public String getReview(){
            return review;
        }
        
        @Override
        public String toString(){
            return id+":"+rating+":"+review;
        }
}