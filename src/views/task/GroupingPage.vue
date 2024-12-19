<script>
import '@/styles/task/GroupingPage.css'
import { ref, computed, onMounted } from 'vue';
import { fetchMenteesByDepartment, fetchAllMentors } from '@/services/GroupingApi.js';
import WhiteBoxComponent from "@/components/WhiteBoxComponent.vue";
import router from "@/router/index.js";

export default {
  components: {WhiteBoxComponent},
  props: {
    templateType: String,
    departmentSeq: String,
    templateSeq: String,
  },
  setup(props) {
    console.log('Props:', props); // 전달받은 값 디버깅
    const { templateType, departmentSeq, templateSeq } = props;

    const data = ref([]); // 전체 데이터 저장
    const groups = ref([]); // 그룹 리스트
    const groupCount = ref(2); // 그룹 개수 (기본값: 3)
    const loading = ref(true); // 로딩 상태
    const error = ref(null); // 에러 상태

    const loadData = async () => {
      try {
        if (templateType === 'JOB' && departmentSeq) {
          const response = await fetchMenteesByDepartment(departmentSeq);
          data.value = response.data.data;
        } else if (templateType === 'NORMAL') {
          const response = await fetchAllMentors();
          data.value = response.data.data;
        } else {
          throw new Error('유효하지 않은 요청입니다.');
        }

        // 그룹 초기화
        createGroups(groupCount.value);
      } catch (err) {
        console.error('데이터 로드 오류:', err);
        error.value = '데이터를 불러오지 못했습니다.';
      } finally {
        loading.value = false;
      }
    };

    // 그룹 생성 함수
    const createGroups = (count) => {
      groups.value = Array.from({ length: count }, (_, index) => ({
        id: index + 1,
        members: [],
      }));
    };

    const handleDragStart = (event, item) => {
      event.dataTransfer.setData('text/plain', JSON.stringify(item));
    };

    const handleDrop = (event, groupId) => {
      event.preventDefault();
      const item = JSON.parse(event.dataTransfer.getData('text/plain'));
      const group = groups.value.find((g) => g.id === groupId);
      if (group && !group.members.some((m) => m.employeeSeq === item.employeeSeq)) {
        group.members.push(item);
      }
    };

    const allowDrop = (event) => {
      event.preventDefault();
    };

    const removeFromGroup = (groupId, member) => {
      const group = groups.value.find((g) => g.id === groupId);
      if (group) {
        group.members = group.members.filter((m) => m.employeeSeq !== member.employeeSeq);
      }
    };

    const draggableItems = computed(() => {
      const assignedIds = groups.value.flatMap((group) => group.members.map((m) => m.employeeSeq));
      return data.value.filter((item) => !assignedIds.includes(item.employeeSeq));
    });

    const saveGroups = async () => {
      // requestData에서 필요한 정보만 선택하여 넘김
      const requestData = {
        tasks: groups.value.map((group, index) => ({
          members: group.members.map((member) => ({
            employeeSeq: member.employeeSeq,
          })),
        })),
      };

      // taskAdd로 요청 데이터 전달, query를 사용해서 URL에 전달
      alert(JSON.stringify({ name: 'TaskAddPage', query: { groupsData: JSON.stringify(requestData) } }));
      router.push({ name: 'TaskAddPage', query: { groupsData: JSON.stringify(requestData) } });
    };


    // 그룹 개수 변경 시 새로운 그룹 갯수에 맞게 그룹 생성
    const onGroupCountChange = () => {
      createGroups(groupCount.value);
    };

    onMounted(loadData);

    return {
      data,
      groups,
      groupCount,
      loading,
      error,
      draggableItems,
      handleDragStart,
      handleDrop,
      allowDrop,
      removeFromGroup,
      saveGroups,
      onGroupCountChange,
    };
  },
};
</script>

<template>
  <div class="total-box">
    <div class="pagetitle">그룹 매칭</div>
    <div v-if="loading">로딩 중입니다...</div>
    <div v-if="error">{{ error }}</div>
    <WhiteBoxComponent>
      <div v-if="data" class="sub-total-box">
        <!-- 그룹 개수 입력 -->
        <div class="group-container">
          <div class="group-line">
            <label for="groupCount">그룹 개수</label>
            <input
                type="number"
                v-model="groupCount"
                @input="onGroupCountChange"
                min="1"
                max="10"
                class="group-num"
            />
          </div>

          <div class="groups">
            <div
                v-for="group in groups"
                :key="group.id"
                class="group"
                @dragover="allowDrop"
                @drop="handleDrop($event, group.id)"
            >
              <div class="group-name">그룹 {{ group.id }}</div>
                <ul class="inline">
                  <li v-for="(member, index) in group.members" :key="index" class="click">
                    {{ member.employeeName }}({{ member.employeeNum }})
                    <button @click="removeFromGroup(group.id, member)" style="margin-left: 10px;">X</button>
                  </li>
                </ul>
              </div>
            <div class="submit-box">
              <button @click="saveGroups" class="submit">그룹 저장</button>
            </div>
          </div>
        </div>

        <div class="line">
          <div class="sub-title">멘티 리스트</div>
          <ul>
            <li
                v-for="(item, index) in draggableItems"
                :key="index"
                draggable="true"
                @dragstart="handleDragStart($event, item)"
                class="list"
            >
              {{ item.employeeName }}({{ item.employeeNum }})
            </li>
          </ul>
        </div>

      </div>
    </WhiteBoxComponent>
  </div>
</template>
