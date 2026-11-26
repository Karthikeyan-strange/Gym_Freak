import { LoaderCircle } from 'lucide-react';

export default function LoadingState({ label = 'Loading data…' }) {
  return <div className="state-box"><LoaderCircle className="spin" size={23} /><span>{label}</span></div>;
}
