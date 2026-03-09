package com.example.springhexagonal.domain.member;

import com.example.springhexagonal.domain.AbstractEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetail extends AbstractEntity {
    private Profile profile;

    private String introduction;

    private LocalDateTime registeredAt;

    private LocalDateTime activatedAt;

    private LocalDateTime deactivatedAt;

    // member aggregate 내 패키지에서만 접근 가능하도록함
    protected static MemberDetail create() {
        MemberDetail memberDetail = new MemberDetail();
        memberDetail.registeredAt = LocalDateTime.now();
        return memberDetail;
    }

    protected void activate() {
        Assert.isTrue(activatedAt == null, "이미 activatedAt은 설정되었습니다.");
        this.activatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        Assert.isTrue(deactivatedAt == null, "이미 deactivatedAt은 설정되었습니다.");
        this.deactivatedAt = LocalDateTime.now();
    }

    protected void updateInfo(MemberInfoUpdateRequest updateRequest) {
        String profileAddress = updateRequest.profileAddress();
        this.profile = profileAddress.isEmpty() ? null : new Profile(profileAddress);
        this.introduction = Objects.requireNonNull(updateRequest.introduction());
    }
}
