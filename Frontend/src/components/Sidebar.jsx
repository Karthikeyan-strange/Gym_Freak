import { Activity, ClipboardList, Dumbbell, LayoutDashboard, Users, UserRound, WalletCards } from 'lucide-react';
import { NavLink } from 'react-router-dom';

const navigation = [
  { label: 'Dashboard', to: '/', icon: LayoutDashboard },
  { label: 'Members', to: '/members', icon: Users },
  { label: 'Trainers', to: '/trainers', icon: UserRound },
  { label: 'Memberships', to: '/memberships', icon: WalletCards },
  { label: 'Workout Plans', to: '/workout-plans', icon: ClipboardList },
  { label: 'Exercises', to: '/exercises', icon: Dumbbell },
];

export default function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="brand">
        <div className="brand-mark"><Activity size={21} strokeWidth={2.5} /></div>
        <div>
          <strong>Gym Freak</strong>
          <span>MANAGEMENT</span>
        </div>
      </div>
      <div className="nav-caption">WORKSPACE</div>
      <nav className="side-nav" aria-label="Main navigation">
        {navigation.map(({ label, to, icon: Icon }) => (
          <NavLink key={to} to={to} end={to === '/'} className={({ isActive }) => `nav-link${isActive ? ' active' : ''}`}>
            <Icon size={19} strokeWidth={1.8} />
            <span>{label}</span>
          </NavLink>
        ))}
      </nav>
      <div className="sidebar-footer">
        <div className="sidebar-footer-icon"><Dumbbell size={17} /></div>
        <div><strong>Stay consistent.</strong><span>Make every rep count.</span></div>
      </div>
    </aside>
  );
}
