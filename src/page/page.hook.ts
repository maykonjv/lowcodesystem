import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

export const usePage = () => {
  const { path, action } = useParams();
  const [form, setForm] = useState({} as any);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    console.log('usePage', path, action);
  }, [action, path]);

  return { path, action, form, setForm, isLoading };
};
