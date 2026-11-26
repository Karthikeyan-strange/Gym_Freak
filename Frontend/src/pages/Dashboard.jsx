import { useEffect, useState } from 'react';
import { Activity, CalendarClock, Users, UserRound, WalletCards } from 'lucide-react';
import DataTable from '../components/DataTable';
import LoadingState from '../components/LoadingState';
import StatusBadge from '../components/StatusBadge';
import StatCard from '../components/StatCard';
import { listMembers } from '../services/memberService';
import { listTrainers } from '../services/trainerService';
import { listMemberships } from '../services/membershipService';
import { listWorkoutPlans } from '../services/workoutPlanService';
import { getErrorMessage } from '../utils/errors';
import { displayName, formatDate } from '../utils/format';

const recentColumns = [
  { key: 'name', label: 'Member' },
  { key: 'email', label: 'Email' },
  { key: 'joinDate', label: 'Join date', render: (row) => formatDate(row.joinDate) },
];

const planColumns = [
  { key: 'planName', label: 'Plan' },
  { key: 'member', label: 'Member', render: (row) => displayName(row, 'member') },
  { key: 'trainer', label: 'Trainer', render: (row) => displayName(row, 'trainer') },
  { key: 'status', label: 'Status', render: (row) => <StatusBadge status={row.status} /> },
];

export default function Dashboard() {
  const [data, setData] = useState({ members: [], trainers: [], memberships: [], plans: [] });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    let active = true;
    Promise.all([listMembers(), listTrainers(), listMemberships(), listWorkoutPlans()])
      .then(([members, trainers, memberships, plans]) => {
        if (active) setData({
          members: Array.isArray(members) ? members : [],
          trainers: Array.isArray(trainers) ? trainers : [],
          memberships: Array.isArray(memberships) ? memberships : [],
          plans: Array.isArray(plans) ? plans : [],
        });
      })
      .catch((requestError) => { if (active) setError(getErrorMessage(requestError)); })
      .finally(() => { if (active) setLoading(false); });
    return () => { active = false; };
  }, []);

  const activeMemberships = data.memberships.filter((item) => item.membershipStatus === 'ACTIVE');
  const expiredMemberships = data.memberships.filter((item) => item.membershipStatus === 'EXPIRED');
  const cutoff = new Date();
  cutoff.setDate(cutoff.getDate() + 30);
  const expiring = activeMemberships.filter((item) => {
    const endDate = new Date(item.endDate);
    return item.endDate && !Number.isNaN(endDate.getTime()) && endDate >= new Date() && endDate <= cutoff;
  }).sort((a, b) => new Date(a.endDate) - new Date(b.endDate));
  const recentMembers = [...data.members].sort((a, b) => new Date(b.joinDate) - new Date(a.joinDate)).slice(0, 5);
  const recentPlans = [...data.plans].sort((a, b) => new Date(b.startDate) - new Date(a.startDate)).slice(0, 5);

  return (
    <section className="dashboard-page">
      <div className="page-intro dashboard-intro">
        <div><p className="eyebrow">OVERVIEW</p><h2>Your gym at a glance</h2><p>Here’s what’s happening at your gym today.</p></div>
      </div>
      {loading ? <LoadingState label="Loading your dashboard…" /> : error ? (
        <div className="error-state">
          <strong>Couldn’t load dashboard data.</strong><span>{error}</span>
          <button type="button" className="button button-secondary" onClick={() => window.location.reload()}>Try again</button>
        </div>
      ) : (
        <>
          <div className="stats-grid">
            <StatCard label="Total Members" value={data.members.length} note="Registered members" icon={Users} tone="green" />
            <StatCard label="Total Trainers" value={data.trainers.length} note="Active coaching team" icon={UserRound} tone="blue" />
            <StatCard label="Active Memberships" value={activeMemberships.length} note="Currently enrolled" icon={WalletCards} tone="purple" />
            <StatCard label="Expired Memberships" value={expiredMemberships.length} note="Need follow-up" icon={Activity} tone="orange" />
          </div>

          <div className="dashboard-grid">
            <section className="panel dashboard-panel">
              <div className="panel-heading"><div><h3>Recently registered members</h3><p>Your newest gym members</p></div><Users size={19} /></div>
              <DataTable columns={recentColumns} rows={recentMembers} rowId="memberId" showActions={false} emptyMessage="New members will appear here." />
            </section>
            <section className="panel dashboard-panel">
              <div className="panel-heading"><div><h3>Recently created workout plans</h3><p>Latest training plans</p></div><Activity size={19} /></div>
              <DataTable columns={planColumns} rows={recentPlans} rowId="planId" showActions={false} emptyMessage="Workout plans will appear here." />
            </section>
          </div>

          <section className="panel expiry-panel">
            <div className="panel-heading"><div><h3>Memberships expiring soon</h3><p>Active memberships ending in the next 30 days</p></div><CalendarClock size={19} /></div>
            {expiring.length ? (
              <div className="expiry-list">
                {expiring.map((membership) => {
                  const daysLeft = Math.ceil((new Date(membership.endDate) - new Date()) / 86400000);
                  return (
                    <div className="expiry-item" key={membership.membershipId}>
                      <div className="member-avatar">{displayName(membership, 'member').slice(0, 1)}</div>
                      <div className="expiry-member"><strong>{displayName(membership, 'member')}</strong><span>{membership.planName || 'Membership'} plan</span></div>
                      <span className="expiry-date">{formatDate(membership.endDate)}</span>
                      <span className={`days-left${daysLeft <= 7 ? ' urgent' : ''}`}>{daysLeft <= 0 ? 'Expires today' : `${daysLeft} days left`}</span>
                    </div>
                  );
                })}
              </div>
            ) : <p className="empty-note">No memberships are due to expire in the next 30 days.</p>}
          </section>
        </>
      )}
    </section>
  );
}
