import { CalendarDays } from 'lucide-react';
import { useLocation } from 'react-router-dom';

const titles = {
  '/': 'Dashboard',
  '/members': 'Members',
  '/trainers': 'Trainers',
  '/memberships': 'Memberships',
  '/workout-plans': 'Workout Plans',
  '/exercises': 'Exercises',
};

export default function Header() {
  const { pathname } = useLocation();
  const title = titles[pathname] || 'Dashboard';
  const date = new Intl.DateTimeFormat('en', { weekday: 'short', month: 'short', day: 'numeric' }).format(new Date());

  return (
    <header className="topbar">
      <div>
        <p className="eyebrow">GYM MANAGEMENT</p>
        <h1>{title}</h1>
      </div>
      <div className="topbar-right">
        <div className="today-label"><CalendarDays size={16} /><span>{date}</span></div>
        <div className="avatar" aria-label="Administrator">A</div>
      </div>
    </header>
  );
}
