import { Navigate, Route, Routes } from 'react-router-dom';
import Sidebar from './components/Sidebar';
import Header from './components/Header';
import Dashboard from './pages/Dashboard';
import Members from './pages/Members';
import Trainers from './pages/Trainers';
import Memberships from './pages/Memberships';
import WorkoutPlans from './pages/WorkoutPlans';
import Exercises from './pages/Exercises';

export default function App() {
  return (
    <div className="app-shell">
      <Sidebar />
      <div className="main-panel">
        <Header />
        <main className="page-content">
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/members" element={<Members />} />
            <Route path="/trainers" element={<Trainers />} />
            <Route path="/memberships" element={<Memberships />} />
            <Route path="/workout-plans" element={<WorkoutPlans />} />
            <Route path="/exercises" element={<Exercises />} />
            <Route path="*" element={<Navigate to="/" replace />} />
          </Routes>
        </main>
      </div>
    </div>
  );
}
