import axios from "axios";

export const downloadFile = async (fileUrl, fileName) => {
    try {
        // fileUrl을 통해 파일 받아오기
        const response = await axios.get(fileUrl, {
            responseType: 'blob',  // 응답을 Blob 형식으로 받기
        });

        // 응답 받은 Blob 데이터를 Object URL로 변환
        const url = window.URL.createObjectURL(new Blob([response.data]));

        // 다운로드를 위한 <a> 태그를 생성합니다.
        const a = document.createElement('a');
        a.href = url;           // Object URL을 a 태그의 href로 설정
        a.download = fileName;  // 다운로드 받을 파일 이름 지정

        // <a> 태그를 body에 추가하고 클릭하여 다운로드를 트리거
        document.body.appendChild(a);
        a.click();

        // 다운로드 후 URL을 해제하여 메모리 해제
        window.URL.revokeObjectURL(url);
    } catch (error) {
        console.error('파일을 다운로드 중 에러가 발생했습니다.:', error);
    }
};