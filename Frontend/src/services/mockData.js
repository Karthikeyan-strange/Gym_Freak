const today = new Date();
const dateFromNow = (days) => {
  const date = new Date(today);
  date.setDate(date.getDate() + days);
  return date.toISOString().slice(0, 10);
};

const seed = {
  members: [
    { memberId: 1, name: 'Jordan Lee', email: 'jordan@example.com', phone: '555-0101', gender: 'Male', age: 28, height: 178, weight: 76, joinDate: dateFromNow(-42) },
    { memberId: 2, name: 'Sam Rivera', email: 'sam@example.com', phone: '555-0102', gender: 'Female', age: 32, height: 165, weight: 61, joinDate: dateFromNow(-28) },
    { memberId: 3, name: 'Alex Morgan', email: 'alex@example.com', phone: '555-0103', gender: 'Other', age: 24, height: 172, weight: 69, joinDate: dateFromNow(-12) },
  ],
  trainers: [
    { trainerId: 1, name: 'Taylor Brooks', email: 'taylor@example.com', phone: '555-0201', specialization: 'Strength', experience: 7, salary: 5200, joiningDate: dateFromNow(-400) },
    { trainerId: 2, name: 'Casey Patel', email: 'casey@example.com', phone: '555-0202', specialization: 'Yoga', experience: 5, salary: 4800, joiningDate: dateFromNow(-260) },
  ],
  memberships: [
    { membershipId: 1, memberId: 1, memberName: 'Jordan Lee', planName: 'Monthly', amount: 49, startDate: dateFromNow(-20), endDate: dateFromNow(10), paymentStatus: 'PAID', membershipStatus: 'ACTIVE' },
    { membershipId: 2, memberId: 2, memberName: 'Sam Rivera', planName: 'Quarterly', amount: 129, startDate: dateFromNow(-100), endDate: dateFromNow(-10), paymentStatus: 'PAID', membershipStatus: 'EXPIRED' },
    { membershipId: 3, memberId: 3, memberName: 'Alex Morgan', planName: 'Monthly', amount: 49, startDate: dateFromNow(-4), endDate: dateFromNow(26), paymentStatus: 'PENDING', membershipStatus: 'ACTIVE' },
  ],
  workoutPlans: [
    { planId: 1, planName: 'Foundation Strength', memberId: 1, memberName: 'Jordan Lee', trainerId: 1, trainerName: 'Taylor Brooks', goal: 'Strength', startDate: dateFromNow(-14), endDate: dateFromNow(46), description: 'A balanced introductory strength program.', status: 'ACTIVE' },
    { planId: 2, planName: 'Mobility & Balance', memberId: 2, memberName: 'Sam Rivera', trainerId: 2, trainerName: 'Casey Patel', goal: 'General Fitness', startDate: dateFromNow(-30), endDate: dateFromNow(30), description: 'Build mobility and balance with a steady weekly routine.', status: 'ACTIVE' },
  ],
  exercises: [
    { exerciseId: 1, planId: 1, planName: 'Foundation Strength', exerciseName: 'Goblet Squat', muscleGroup: 'Legs', sets: 3, reps: 10, restSeconds: 60, weight: 18, instructions: 'Keep your chest lifted and knees tracking over your toes.' },
    { exerciseId: 2, planId: 2, planName: 'Mobility & Balance', exerciseName: 'Dumbbell Row', muscleGroup: 'Back', sets: 3, reps: 12, restSeconds: 45, weight: 12, instructions: 'Keep your back neutral and pull the elbow toward your hip.' },
  ],
};

const collections = structuredClone(seed);

const delay = () => new Promise((resolve) => setTimeout(resolve, 180));
const idFields = {
  members: 'memberId',
  trainers: 'trainerId',
  memberships: 'membershipId',
  workoutPlans: 'planId',
  exercises: 'exerciseId',
};

function addRelatedNames(collection, data) {
  if (collection === 'memberships' || collection === 'workoutPlans') {
    const member = collections.members.find((item) => String(item.memberId) === String(data.memberId));
    if (member) data.memberName = member.name;
  }
  if (collection === 'workoutPlans') {
    const trainer = collections.trainers.find((item) => String(item.trainerId) === String(data.trainerId));
    if (trainer) data.trainerName = trainer.name;
  }
  if (collection === 'exercises') {
    const plan = collections.workoutPlans.find((item) => String(item.planId) === String(data.planId));
    if (plan) data.planName = plan.planName;
  }
  return data;
}

export async function listMock(collection) {
  await delay();
  return structuredClone(collections[collection]);
}

export async function getMock(collection, id) {
  await delay();
  const record = collections[collection].find((item) => String(item[idFields[collection]]) === String(id));
  if (!record) throw new Error('Record not found.');
  return structuredClone(record);
}

export async function createMock(collection, data) {
  await delay();
  const idField = idFields[collection];
  const nextId = Math.max(0, ...collections[collection].map((item) => Number(item[idField]) || 0)) + 1;
  const record = { ...addRelatedNames(collection, { ...data }), [idField]: nextId };
  collections[collection].push(record);
  return structuredClone(record);
}

export async function updateMock(collection, id, data) {
  await delay();
  const idField = idFields[collection];
  const index = collections[collection].findIndex((item) => String(item[idField]) === String(id));
  if (index < 0) throw new Error('Record not found.');
  collections[collection][index] = { ...collections[collection][index], ...addRelatedNames(collection, { ...data }) };
  return structuredClone(collections[collection][index]);
}

export async function deleteMock(collection, id) {
  await delay();
  const idField = idFields[collection];
  const index = collections[collection].findIndex((item) => String(item[idField]) === String(id));
  if (index < 0) throw new Error('Record not found.');
  collections[collection].splice(index, 1);
}

export async function renewMock(id) {
  const current = await getMock('memberships', id);
  const nextStart = current.endDate && new Date(current.endDate) > today ? current.endDate : dateFromNow(0);
  const endDate = new Date(`${nextStart}T00:00:00`);
  endDate.setMonth(endDate.getMonth() + (current.planName === 'Yearly' ? 12 : current.planName === 'Half-Yearly' ? 6 : current.planName === 'Quarterly' ? 3 : 1));
  const renewed = await createMock('memberships', {
    ...current,
    startDate: nextStart,
    endDate: endDate.toISOString().slice(0, 10),
    membershipStatus: 'ACTIVE',
    paymentStatus: 'PENDING',
  });
  return renewed;
}

export async function listMockActiveMemberships() {
  return (await listMock('memberships')).filter((item) => item.membershipStatus === 'ACTIVE');
}

export async function listMockExpiredMemberships() {
  return (await listMock('memberships')).filter((item) => item.membershipStatus === 'EXPIRED');
}

export async function listMockPlansByMember(id) {
  return (await listMock('workoutPlans')).filter((item) => String(item.memberId) === String(id));
}

export async function listMockPlansByTrainer(id) {
  return (await listMock('workoutPlans')).filter((item) => String(item.trainerId) === String(id));
}

export async function listMockExercisesByPlan(id) {
  return (await listMock('exercises')).filter((item) => String(item.planId) === String(id));
}
