import axios from 'axios';

// 위키 등록
export const createWiki = async(wikiData) => {
    try {
        return await axios.post(`http://localhost:8253/api/v1/wiki`, wikiData);
    } catch(error) {
        console.error("위키를 등록하던 중 오류가 발생했습니다.", error);
    }
}

// 위키 리스트 조회
export const fetchWikiList = async() => {
    try {
        const response = await axios.get(`http://localhost:8253/api/v1/wiki`);
        return response.data;
    } catch(error) {
        console.error('위키 리스트를 가져오는 중 오류가 발생헸습니다.', error);
    }
}

// 위키 내용 조회
export const fetchWiki = async(wikiSeq) => {
    try {
        const response = await axios.get(`http://localhost:8253/api/v1/wiki/${wikiSeq}`);
        return response.data;
    } catch(error) {
        console.error('위키 내용을 가져오는 중 오류가 발생헸습니다.', error);
    }
}

// 위키 수정
export const updateWiki = async(wikiSeq, wikiData) => {
    try {
        return await axios.put(`http://localhost:8253/api/v1/wiki/${wikiSeq}`, wikiData);
    } catch(error) {
        console.error("위키를 수정하던 중 오류가 발생했습니다.", error);
    }
}