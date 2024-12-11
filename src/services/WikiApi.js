import axios from 'axios';

export const createWiki = async(wikiData) => {
    try {
        const response =  await axios.post(`http://localhost:8253/api/v1/wiki`, wikiData);
        return response.data;
    } catch(error) {
        console.error("위키를 등록하던 중 오류가 발생했습니다.", error);
    }
}