import OnboardingDataPage from "@/views/onboarding/OnboardingDataPage.vue";
import BreakApplyPage from "@/views/onboarding/BreakApplyPage.vue";
import ConferenceRoomPage from "@/views/onboarding/ConferenceRoomPage.vue";

export default [
    {
        path: '/onboarding/design',
        component: OnboardingDataPage
    },
    {
        path: '/break',
        component: BreakApplyPage,
    },
    {
        path: '/onboarding/conferenceRoom',
        component: ConferenceRoomPage
    }
]
