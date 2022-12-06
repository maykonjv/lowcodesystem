import { CreatePage } from '../page/create/create';
import { CustomPage } from '../page/custom';
import { EditPage } from '../page/edit/edit';
import { SearchPage } from '../page/search/search';
import { ViewPage } from '../page/view/view';
import { Navigate, Route, Routes } from 'react-router-dom';

export function ProtectedRoute(props: any) {
  if (!window.sessionStorage.getItem('auth')) {
    return <Navigate to='/' />;
  }
  return props.children;
}

export function App() {
  return (
    <Routes>
      <Route path='/' element={<CustomPage />} />
      <Route path='/:path' element={<CustomPage />} />
      <Route path='/:path/create' element={<CreatePage />} />
      <Route path='/:path/search' element={<SearchPage />} />
      <Route path='/:path/edit/:id' element={<EditPage />} />
      <Route path='/:path/view/:id' element={<ViewPage />} />
    </Routes>
  );
}
