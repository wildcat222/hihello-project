<template>
    <div class="peer-review__container">
        <!-- 헤더 -->
        <h1>동료 평가 조회</h1>

        <!-- 리스트 헤더와 드롭다운 -->
        <div class="peer-review__header-wrapper">
            <div></div> <!-- 빈 공간 (왼쪽 정렬을 위해) -->
            <div class="peer-review__dropdown-wrapper">
                <select class="peer-review__dropdown">
                    <option>어린왕자 읽고 독후감쓰기</option>
                </select>
                <select class="peer-review__dropdown peer-review__group-dropdown">
                    <option>1조</option>
                </select>
            </div>
        </div>

        <!-- 리스트 컴포넌트 -->
        <white-box>
            <div class="peer-review__list-wrapper">
                <div class="peer-review__list-header">
                    <div class="peer-review__list-column">피평가자</div>
                    <div class="peer-review__list-column">사번</div>
                    <div class="peer-review__list-column">팀</div>
                    <div class="peer-review__list-column">평가자</div>
                    <div class="peer-review__list-column">배점</div>
                    <div class="peer-review__list-column">점수</div>
                    <div class="peer-review__list-column">조회</div>
                </div>

                <div v-if="peerReview.length > 0">
                    <list-component :items="peerReview">
                        <template #item="{ item }">
                            <div class="peer-review__result-item">
                                <div class="peer-review__list-column">{{ item.name }}</div>
                                <div class="peer-review__list-column">{{ item.id }}</div>
                                <div class="peer-review__list-column">{{ item.team }}</div>
                                <div class="peer-review__list-column">{{ item.reviewer }}</div>
                                <div class="peer-review__list-column">{{ item.total }}</div>
                                <div class="peer-review__list-column">{{ item.score }}</div>
                                <div class="peer-review__list-column">
                                    <button class="peer-review__detail-btn">상세 조회</button>
                                </div>
                            </div>
                        </template>
                    </list-component>
                </div>
                <div v-else>
                    <p class="peer-review__no-data">동료 평가 데이터가 없습니다.</p>
                </div>
            </div>
        </white-box>
    </div>
</template>




<script setup>
import "@/styles/peer-review/PeerReview.css";
import WhiteBox from "@/components/WhiteBoxComponent.vue";
import ListComponent from "@/components/ListComponent.vue";

const peerReview = [
    { name: "김인턴", id: "1312165", team: "기획 1팀", reviewer: "김인턴", total: 10, score: 8 },
    { name: "권인턴", id: "1312165", team: "개발 1팀", reviewer: "박인턴", total: 10, score: 7 },
    { name: "박인턴", id: "1312165", team: "마케팅 1팀", reviewer: "김인턴", total: 10, score: 10 },
];
</script>

<style scoped>
.white-box{
    margin-top: 0;
    padding-top: 1vw;
}

.no-data {
    color: gray;
    margin-top: 20px;
}
</style>
