import { Navigate, Route, Routes } from 'react-router-dom';
import { Page } from '../page/page';

export function ProtectedRoute(props: any) {
	if (!window.sessionStorage.getItem('auth')) {
		return <Navigate to="/" />;
	}
	return props.children;
}

export function App() {
	return (
		<Routes>
			<Route path="/" element={<Page />} />
			<Route path="/:path" element={<Page />} />
			<Route path="/:path/:action" element={<Page />} />
		</Routes>
	);
}
