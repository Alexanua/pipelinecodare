package io.MajdiCodarePipeline.pipelinecodare.Repository;

import io.MajdiCodarePipeline.pipelinecodare.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MySqlRepository extends JpaRepository<Message, Long> {
}