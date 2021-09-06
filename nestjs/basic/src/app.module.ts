import { Module } from '@nestjs/common';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { TypeOrmModule } from '@nestjs/typeorm';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { join } from 'path';
import { ArticleModule } from './article/article.module';
import { TagModule } from './tag/tag.module';
import configuration from 'src/config/cofiguration';

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
      load: [configuration],
    }),
    ConfigModule.forRoot(),
    TypeOrmModule.forRootAsync({
      imports: [ConfigModule],
      inject: [ConfigService],
      useFactory: (configService: ConfigService) => ({
        type: 'mysql',
        host: configService.get('database.host'),
        port: configService.get('database.port'),
        username: configService.get('database.user'),
        password: configService.get('database.password'),
        database: configService.get('database.name'),
        //entities: [join(__dirname, '/**/*.entity.ts')],
        //entities: ['/**/*.entities/*.js'],

        //entities: ['/**/*.entity.ts'],
        //entities: ['/**/*.entity{.ts,.js}'],
        entities: [join(__dirname, '/**/*.entity{.ts,.js}')],
        logging: true,
        synchronize: false,
      }),
    }),
    ArticleModule,
    TagModule,
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
