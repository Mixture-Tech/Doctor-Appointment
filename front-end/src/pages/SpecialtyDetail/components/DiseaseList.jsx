import React, { useState, useEffect } from 'react';
import axiosClient from '../../../services/apis/axiosClient';
import { useParams } from 'react-router-dom';

export default function DiseaseList() {
    const { id } = useParams();
    const [diseases, setDiseases] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchDiseasesBySpecialization = async () => {
            try {
                const response = await axiosClient.get(`/diseases/${id}`);
                console.log('Diseases Response:', response);
                if (response.data) {
                    setDiseases(response.data);
                }
            } catch (err) {
                console.error('Error fetching diseases:', err);
                setError('Không có bệnh cho chuyên khoa này');
            } finally {
                setLoading(false);
            }
        };

        if (id) {
            fetchDiseasesBySpecialization();
        }
    }, [id]);

    if (loading) return <div>Đang tải danh sách bệnh...</div>;
    if (error) return <div>{error}</div>;

    return (
        <section className="mb-8">
            <h2 className="text-2xl font-semibold">Danh sách bệnh</h2>
            {diseases.length === 0 ? (
                <p className="text-gray-700 mt-4">Không có bệnh cho chuyên khoa này.</p>
            ) : (
                <ul className="list-disc list-inside mt-4 space-y-2 text-gray-700 md:text-sm">
                    {diseases.map((disease) => (
                        <li key={disease.disease_id}>{disease.disease_vie_name}</li>
                    ))}
                </ul>
            )}
        </section>
    );
}
