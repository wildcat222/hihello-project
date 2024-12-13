import WikiCreatePage from "@/views/wiki/WikiCreatePage.vue";
import WikiUpdatePage from "@/views/wiki/WikiUpdatePage.vue";

export default [
    {
        path: '/wiki/newpost',
        component: WikiCreatePage
    },
    {
        path: '/wiki/newpost/:wikiSeq',
        component: WikiUpdatePage
    }
]