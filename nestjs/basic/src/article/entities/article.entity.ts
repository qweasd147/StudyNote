import { Column, OneToMany, PrimaryGeneratedColumn, Entity } from 'typeorm';
import { Tag } from 'src/tag/entities/tag.entity';

@Entity()
export class Article {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  subject: string;

  @Column()
  content: string;

  @Column()
  count: number;

  @OneToMany((type) => Tag, (tag) => tag.article)
  tags: Tag[];
}
