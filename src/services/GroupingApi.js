import {springAPI} from "@/services/axios.js";

export const fetchMenteesByDepartment = (departmentSeq) => {
    return springAPI.get(`/hr/mentee/${departmentSeq}`);
};

export const fetchAllMentors = () => {
    return springAPI.get('/hr/mentor');
};
export const saveGroupsAPI = (groupData) => {
    return springAPI.post('/hr/matching/group', groupData);
};