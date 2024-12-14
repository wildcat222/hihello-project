import WikiCreatePage from "@/views/wiki/WikiCreatePage.vue";
import WikiUpdatePage from "@/views/wiki/WikiUpdatePage.vue";
import WikiDetailReadPage from "@/views/wiki/WikiDetailReadPage.vue";
import WikiListReadPage from "@/views/wiki/WikiListReadPage.vue";

export default [
    {
        path: '/wiki',
        component: WikiListReadPage
    },
    {
        path: '/wiki/:wikiSeq',
        component: WikiDetailReadPage,
        props: true
    },
    {
        path: '/wiki/newpost',
        component: WikiCreatePage
    },
    {
        path: '/wiki/newpost/:wikiSeq',
        component: WikiUpdatePage
    }
]