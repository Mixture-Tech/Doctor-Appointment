import React, { useEffect, useState } from 'react';
import axiosClient from '../../services/apis/axiosClient';
import Speciality from './components/Speciality';

export default function Specialities() {
  const [specialties, setSpecialties] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchSpecialties = async () => {
      try {
        const response = await axiosClient.get("/specializations");
        if (Array.isArray(response.data)) {
          setSpecialties(response.data); // Truyền đúng dữ liệu vào state
        } else {
          setError("Dữ liệu không hợp lệ");
        }
        setLoading(false);
      } catch (err) {
        setError("Lỗi khi tải dữ liệu chuyên khoa");
        setLoading(false);
      }
    };

    fetchSpecialties();
  }, []);

  if (loading) {
    return <div>Đang tải dữ liệu...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  return (
    <main>
      <Speciality item={specialties} />
    </main>
  );
}
