import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Doctors from "./components/Doctors";
import axiosClient from "../../../services/apis/axiosClient";

export default function ListDoctors() {
  const [doctors, setDoctors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchDoctors = async () => {
      try {
        const response = await axiosClient.get("/doctors");
        if (response.data && Array.isArray(response.data)) {
          const formattedDoctors = response.data.map((doctor) => ({
            id: doctor.doctor_id,
            name: doctor.doctor_name,
            imageUrl: doctor.doctor_image,
            specialty: doctor.specialization_name,
          }));
          setDoctors(formattedDoctors);
        } else {
          setError("Dữ liệu không hợp lệ.");
        }
        setLoading(false);
      } catch (err) {
        setError("Lỗi khi tải danh sách bác sĩ.");
        setLoading(false);
      }
    };

    fetchDoctors();
  }, []);

  const handleDoctorClick = (doctorId) => {
    navigate(`/chi-tiet-bac-si/${doctorId}`);
  };

  if (loading) {
    return <div>Đang tải dữ liệu...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  return (
    <main>
      <Doctors item={doctors} onDoctorClick={handleDoctorClick} />
    </main>
  );
}
