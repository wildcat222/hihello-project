import axios from 'axios';

export default {
    async updateChecklistStatus(checklistStatusSeq, checklistSeq, listCheckedStatus) {
        try {
            const payload = {
                checklistStatusSeq,
                checklistSeq,
                listCheckedStatus,
            };

            const response = await axios.put('http://localhost:8253/api/v1/hr/onboarding/checklist', payload, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
                },
            });

            if (response.data.success) {
                console.log('체크리스트 상태 업데이트 성공:', response.data.message);
            } else {
                console.error('체크리스트 상태 업데이트 실패:', response.data.message);
            }
        } catch (error) {
            console.error('API 호출 에러:', error);
        }
    },

    async fetchOnboardingData() {
        try {
            const response = await axios.get('http://localhost:8253/api/v1/mentee/onboarding', {
                params: { taskContent: this.query },
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`,
                },
            });

            if (response.data.success) {
                this.onboardingList = this.groupChecklistByTemplate(response.data.data.onboardingList);

                // 완료된 항목과 전체 항목의 개수 계산
                this.completedCount = this.onboardingList.filter(item => item.onboardingCompletedStatus).length;
                this.totalCount = this.onboardingList.length;
            } else {
                console.error('데이터 호출 실패:', response.data.message);
            }
        } catch (error) {
            console.error('API 호출 에러:', error);
        } finally {
            this.loading = false;
        }
    },

    groupChecklistByTemplate(onboardingList) {
        const groupedItems = [];

        onboardingList.forEach(item => {
            let template = groupedItems.find(group => group.templateSub === item.templateSub);

            if (!template) {
                template = {
                    templateSub: item.templateSub,
                    templateDetail: item.templateDetail,
                    templateType: item.templateType,
                    checklistContent: [],
                    onboardingCompletedStatus: item.onboardingCompletedStatus,
                    templateUrl: item.templateUrl
                };
                groupedItems.push(template);
            }

            if (item.checklistContent) {
                template.checklistContent.push({
                    checklistStatusSeq: item.checklistStatusSeq,
                    checklistSeq: item.checklistSeq,
                    checklistContent: item.checklistContent,
                    listCheckedStatus: item.listCheckedStatus
                });
            }
        });

        return groupedItems;
    },

    goToUrl(url) {
        console.log('Received URL:', url); // URL 값 확인
        if (url) {
            window.open(url, '_blank'); // URL이 정상일 경우 새 탭에서 열기
        } else {
            console.error('URL is null or undefined.');
        }
    },

    // 체크리스트 항목의 상태 변경 (체크박스 개별적으로 처리)
    toggleChecklistStatus(item, content) {
        const updatedStatus = !content.listCheckedStatus;  // 상태 반전
        this.updateChecklistStatus(content.checklistStatusSeq, content.checklistSeq, updatedStatus);
        content.listCheckedStatus = updatedStatus;  // 상태 변경
    },
};
