ALTER TABLE system_stats_entity
    ADD COLUMN used_disk_gb DOUBLE PRECISION NOT NULL DEFAULT 0;

ALTER TABLE system_stats_entity
    ADD COLUMN total_disk_gb DOUBLE PRECISION NOT NULL DEFAULT 0;

ALTER TABLE system_stats_entity
    ALTER COLUMN used_disk_gb DROP DEFAULT;

ALTER TABLE system_stats_entity
    ALTER COLUMN total_disk_gb DROP DEFAULT;