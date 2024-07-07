package com.Imtiaz.BookSocialNetwork.book;

import java.util.List;

import com.Imtiaz.BookSocialNetwork.common.BaseEntity;
import com.Imtiaz.BookSocialNetwork.feedback.Feedback;
import com.Imtiaz.BookSocialNetwork.history.BookTransactionHistory;
import com.Imtiaz.BookSocialNetwork.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Book extends BaseEntity {

    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private String bookCover;
    private boolean archived;
    private boolean shareable;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "book")
    private List<Feedback> feedback;

    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> histories;

    @Transient
    public double getRate() {
        if (feedback == null || feedback.isEmpty()) {
            return 0.0;
        }
        var rate = this.feedback.stream()
                        .mapToDouble(Feedback::getNote)
                        .average()
                        .orElse(0.0);
        double roundedRare = Math.round(rate*10.0)/10.0;
        return roundedRare;
    }

}
